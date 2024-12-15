const fileName = JSON_FILE;
const url = URL;

console.log(`file name found: ${fileName}`);
console.log(`url name found: ${url}`);

const queryURL = `http://localhost:8080/update-mapping?fileName=${fileName}&url=${url}`;

const response = http.get(queryURL);