const API_URL = "http://localhost:8080/kv";


// PUT
async function putValue() {

    const key = document.getElementById("key").value;
    const value = document.getElementById("value").value;

    if (!key || !value) {
        alert("Please enter both key and value.");
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${key}`, {
            method: "PUT",
            headers: {
                "Content-Type": "text/plain"
            },
            body: value
        });

        const result = await response.text();

        alert("Value stored successfully: " + result);

    } catch (error) {

        alert("Could not connect to MiniKV server.");

    }
}


// GET
async function getValue() {

    const key = document.getElementById("getKey").value;

    if (!key) {
        alert("Please enter a key.");
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${key}`);

        const result = await response.text();

        document.getElementById("getResult").textContent =
            "Value: " + result;

    } catch (error) {

        document.getElementById("getResult").textContent =
            "Could not connect to MiniKV server.";

    }
}


// DELETE
async function deleteValue() {

    const key = document.getElementById("deleteKey").value;

    if (!key) {
        alert("Please enter a key.");
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${key}`, {
            method: "DELETE"
        });

        const result = await response.text();

        document.getElementById("deleteResult").textContent =
            "Result: " + result;

    } catch (error) {

        document.getElementById("deleteResult").textContent =
            "Could not connect to MiniKV server.";

    }
}


// SIZE
async function getSize() {

    try {

        const response = await fetch(API_URL);

        const result = await response.text();

        document.getElementById("sizeResult").textContent =
            "Current size: " + result;

    } catch (error) {

        document.getElementById("sizeResult").textContent =
            "Could not connect to MiniKV server.";

    }
}