package com.lotusinc.transportmanagmentsystem.attendance

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lotusinc.transportmanagmentsystem.attendance.model.Van
import com.lotusinc.transportmanagmentsystem.attendance.model.VanAttendance
import com.lotusinc.transportmanagmentsystem.attendance.model.VanWithAttendance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AttendanceViewModel : ViewModel() {

    private val _vans = MutableStateFlow<List<Van>>(emptyList())
    val vans: StateFlow<List<Van>> = _vans

    init {
        loadVans()
    }

    private fun loadVans() {
        fetchVans { fetchedVans ->
            _vans.value = fetchedVans
        }
    }

    private val firebaseAuth = FirebaseAuth.getInstance()
    fun markAttendance(vanId: String, date: String, onResult: (Boolean) -> Unit) {
        val firestore = Firebase.firestore
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date())
        val userId = firebaseAuth.currentUser?.uid ?: return
        val attendance = hashMapOf(
            "userId" to userId,
            "vanId" to vanId,
            "date" to date
        )
        val attendanceRef = firestore.collection("attendance")
            .document("${userId}_$todayDate")
        attendanceRef.set(attendance)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun checkAttendance(callback: (Boolean) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val userId = firebaseAuth.currentUser?.uid ?: return
        // Get today's date in the format yyyy-MM-dd
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date())

        val attendanceRef = firestore.collection("attendance")
            .document("${userId}_$todayDate")

        attendanceRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Attendance exists for today
                    callback(true)
                } else {
                    // No attendance for today
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(false)
            }
    }

    private fun fetchVans(callback: (List<Van>) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val vansCollection = firestore.collection("vans")

        vansCollection.get()
            .addOnSuccessListener { snapshot ->
                val vansList = snapshot.documents.mapNotNull { document ->
                    val driverName = document.getString("driver") ?: ""
                    val number = document.getString("number") ?: ""
                    val route = document.get("route") as? List<String> ?: emptyList()

                    Van(driver = driverName, number = number, route = route)
                }
                callback(vansList)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(emptyList()) // If there's an error, return an empty list
            }
    }


    fun fetchAttendanceDetails(callback: (List<VanAttendance>) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date())

        firestore.collection("attendance")
            .whereEqualTo("date", todayDate) // Filter by today's date
            .get()
            .addOnSuccessListener { attendanceSnapshot ->
                val vanAttendanceMap = mutableMapOf<String, MutableList<String>>()

                // Build a map of vanId -> userId list
                attendanceSnapshot.documents.forEach { document ->
                    val vanId = document.getString("vanId")
                    val userId = document.getString("userId")

                    if (vanId != null && userId != null) {
                        if (!vanAttendanceMap.containsKey(vanId)) {
                            vanAttendanceMap[vanId] = mutableListOf()
                        }
                        vanAttendanceMap[vanId]?.add(userId)
                    }
                }

                // Fetch user names for each userId
                val vanAttendanceList = mutableListOf<VanAttendance>()
                val userFetchTasks = mutableListOf<Task<QuerySnapshot>>()

                for ((vanId, userIds) in vanAttendanceMap) {
                    val userFetchTask = firestore.collection("UserInfo")
                        .whereIn(FieldPath.documentId(), userIds)
                        .get()
                        .addOnSuccessListener { userSnapshot ->
                            val studentNames = userSnapshot.documents.mapNotNull { it.getString("email") }
                            vanAttendanceList.add(
                                VanAttendance(
                                    vanId = vanId,
                                    count = studentNames.size,
                                    listOfStudents = studentNames
                                )
                            )
                        }
                    userFetchTasks.add(userFetchTask)
                }

                // Wait for all user fetch tasks to complete
                Tasks.whenAllComplete(userFetchTasks)
                    .addOnSuccessListener {
                        callback(vanAttendanceList)
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        callback(emptyList())
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(emptyList())
            }
    }


    fun fetchVansAndAttendance(callback: (List<VanWithAttendance>) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("vans").get()
            .addOnSuccessListener { snapshot ->
                val vans = snapshot.documents.mapNotNull { document ->
                    val driverName = document.getString("driver") ?: ""
                    val number = document.getString("number") ?: ""
                    val route = document.get("route") as? List<String> ?: emptyList()

                    Van(driver = driverName, number = number, route = route)
                }

                // Fetch attendance details
                fetchAttendanceDetails { attendanceList ->
                    val combined = vans.map { van ->
                        val attendance = attendanceList.find { it.vanId == van.number }
                        VanWithAttendance(
                            van = van,
                            attendanceCount = attendance?.count ?: 0,
                            studentNames = attendance?.listOfStudents ?: emptyList()
                        )
                    }

                    callback.invoke(combined)
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback.invoke(emptyList())
            }
    }
}



