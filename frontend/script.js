const API_URL = "http://localhost:8080/kv";


// ==================== PUT ====================

async function putValue() {

    const keyInput = document.getElementById("key");
    const valueInput = document.getElementById("value");

    const key = keyInput.value.trim();
    const value = valueInput.value.trim();

    if (!key || !value) {
        alert("Please enter both key and value.");
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${encodeURIComponent(key)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "text/plain"
            },
            body: value
        });

        if (!response.ok) {
            throw new Error("PUT request failed");
        }

        alert("Value stored successfully.");

        // Clear inputs
        keyInput.value = "";
        valueInput.value = "";

        // Move cursor back to Key
        keyInput.focus();

        // Update total keys
        getSize();

    } catch (error) {

        alert("Could not connect to MiniKV server.");

    }
}


// ==================== GET ====================

async function getValue() {

    const keyInput = document.getElementById("getKey");

    const key = keyInput.value.trim();

    if (!key) {
        alert("Please enter a key.");
        return;
    }

    try {

        const response = await fetch(
            `${API_URL}/${encodeURIComponent(key)}`
        );

        if (!response.ok) {
            throw new Error("GET request failed");
        }

        const result = await response.text();

        document.getElementById("getResult").textContent =
            "Value: " + result;

    } catch (error) {

        document.getElementById("getResult").textContent =
            "Could not connect to MiniKV server.";

    }
}


// ==================== DELETE ====================

async function deleteValue() {

    const keyInput = document.getElementById("deleteKey");

    const key = keyInput.value.trim();

    if (!key) {
        alert("Please enter a key.");
        return;
    }

    try {

        const response = await fetch(
            `${API_URL}/${encodeURIComponent(key)}`,
            {
                method: "DELETE"
            }
        );

        if (!response.ok) {
            throw new Error("DELETE request failed");
        }

        const result = await response.text();

        document.getElementById("deleteResult").textContent =
            "Result: " + result;

        keyInput.value = "";

        keyInput.focus();

        // Update total keys
        getSize();

    } catch (error) {

        document.getElementById("deleteResult").textContent =
            "Could not connect to MiniKV server.";

    }
}


// ==================== SIZE ====================

async function getSize() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("SIZE request failed");
        }

        const result = await response.text();

        document.getElementById("sizeResult").textContent = result;

    } catch (error) {

        document.getElementById("sizeResult").textContent = "0";

    }
}


// ==================== ENTER KEY ====================

// Store: Key → Enter → Value
document.getElementById("key").addEventListener("keydown", function(event) {

    if (event.key === "Enter") {

        event.preventDefault();

        document.getElementById("value").focus();
    }
});


// Store: Value → Enter → PUT
document.getElementById("value").addEventListener("keydown", function(event) {

    if (event.key === "Enter") {

        event.preventDefault();

        putValue();
    }
});


// Get: Enter → GET
document.getElementById("getKey").addEventListener("keydown", function(event) {

    if (event.key === "Enter") {

        event.preventDefault();

        getValue();
    }
});


// Delete: Enter → DELETE
document.getElementById("deleteKey").addEventListener("keydown", function(event) {

    if (event.key === "Enter") {

        event.preventDefault();

        deleteValue();
    }
});


// Load current size when page opens
getSize();