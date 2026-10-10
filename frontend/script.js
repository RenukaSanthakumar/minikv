const API_URL = "http://localhost:8080/kv";

const $ = (id) => document.getElementById(id);


// ==================== SERVER STATUS ====================

function updateStatus(isOnline) {
    const state = isOnline ? "online" : "offline";
    const label = isOnline ? "Server Online" : "Server Offline";
    const backendLabel = isOnline ? "Connected" : "Disconnected";

    $("serverStatus").textContent = label;
    $("backendStatus").textContent = backendLabel;
    $("apiStatus").textContent = isOnline ? "API Online" : "API Offline";

    $("sidebarDot").className = `status-dot ${state}`;
    $("apiDot").className = `status-dot ${state}`;
    $("apiBadge").className = `api-badge ${state}`;
}

async function checkServer() {
    try {
        const response = await fetch(API_URL, {
            method: "GET",
            cache: "no-store"
        });

        // A server response means the backend is reachable.
        updateStatus(true);

        if (response.ok) {
            const count = await response.text();
            $("sizeResult").textContent = count.trim();
        }
    } catch (error) {
        updateStatus(false);
        $("sizeResult").textContent = "—";
        $("countResult").textContent = "Server unavailable.";
    }
}


// ==================== PUT ====================

async function putValue() {
    const keyInput = $("key");
    const valueInput = $("value");
    const key = keyInput.value.trim();
    const value = valueInput.value;

    if (!key || !value.trim()) {
        $("putResult").textContent =
            "Please enter both a key and a value.";
        return;
    }

    $("putButton").disabled = true;
    $("putResult").textContent = "Storing value...";

    try {
        const response = await fetch(
            `${API_URL}/${encodeURIComponent(key)}`,
            {
                method: "PUT",
                headers: { "Content-Type": "text/plain" },
                body: value
            }
        );

        const result = await response.text();

        if (!response.ok) {
            throw new Error(result || "Store request failed.");
        }

        $("putResult").textContent =
            `Successfully stored "${key}".`;

        keyInput.value = "";
        valueInput.value = "";

        await refreshCount();
        await checkServer();
        keyInput.focus();

    } catch (error) {
        $("putResult").textContent =
            "Could not store the value. Check that the backend is running.";
        await checkServer();
    } finally {
        $("putButton").disabled = false;
    }
}


// ==================== GET ====================

async function getValue() {
    const keyInput = $("getKey");
    const key = keyInput.value.trim();

    if (!key) {
        $("getResult").textContent = "Please enter a key.";
        keyInput.focus();
        return;
    }

    $("getButton").disabled = true;
    $("getResult").textContent = "Retrieving value...";

    try {
        const response = await fetch(
            `${API_URL}/${encodeURIComponent(key)}`,
            { cache: "no-store" }
        );

        const result = await response.text();

        if (!response.ok) {
            throw new Error("GET request failed.");
        }

        if (result === "null") {
            $("getResult").textContent =
                `No value found for "${key}".`;
        } else {
            $("getResult").textContent = `Value: ${result}`;
        }

        await checkServer();

    } catch (error) {
        $("getResult").textContent =
            "Could not retrieve the value. Check the backend connection.";
        await checkServer();
    } finally {
        $("getButton").disabled = false;
    }
}


// ==================== DELETE ====================

async function deleteValue() {
    const keyInput = $("deleteKey");
    const key = keyInput.value.trim();

    if (!key) {
        $("deleteResult").textContent = "Please enter a key.";
        keyInput.focus();
        return;
    }

    $("deleteButton").disabled = true;
    $("deleteResult").textContent = "Deleting value...";

    try {
        const response = await fetch(
            `${API_URL}/${encodeURIComponent(key)}`,
            { method: "DELETE" }
        );

        const result = await response.text();

        if (!response.ok) {
            throw new Error("DELETE request failed.");
        }

        $("deleteResult").textContent =
            `Delete request completed: ${result}`;

        keyInput.value = "";

        await refreshCount();
        await checkServer();
        keyInput.focus();

    } catch (error) {
        $("deleteResult").textContent =
            "Could not delete the value. Check the backend connection.";
        await checkServer();
    } finally {
        $("deleteButton").disabled = false;
    }
}


// ==================== COUNT / SIZE ====================

async function refreshCount() {
    const response = await fetch(API_URL, { cache: "no-store" });

    if (!response.ok) {
        throw new Error("Could not retrieve key count.");
    }

    const count = (await response.text()).trim();

    $("sizeResult").textContent = count;
    $("countResult").textContent = `Total keys: ${count}`;

    return count;
}

async function getSize() {
    $("countButton").disabled = true;
    $("countResult").textContent = "Checking...";

    try {
        await refreshCount();
        await checkServer();
    } catch (error) {
        $("countResult").textContent =
            "Could not retrieve the count. Check the backend.";
        await checkServer();
    } finally {
        $("countButton").disabled = false;
    }
}


// ==================== VIEW ALL DATA ====================

async function getAllValues() {
    $("allButton").disabled = true;
    $("allResult").textContent = "Loading stored data...";

    try {
        const response = await fetch(`${API_URL}/all`, {
            cache: "no-store"
        });

        if (!response.ok) {
            throw new Error("Could not retrieve stored data.");
        }

        const data = await response.json();

        $("allResult").textContent =
            Object.keys(data).length === 0
                ? "No keys are currently stored."
                : JSON.stringify(data, null, 2);

        await checkServer();

    } catch (error) {
        $("allResult").textContent =
            "Could not load data. Check the backend and /kv/all endpoint.";
        await checkServer();
    } finally {
        $("allButton").disabled = false;
    }
}


// ==================== ENTER KEY SUPPORT ====================

// Store: Key → Enter → Value → Enter → Store
$("key").addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        $("value").focus();
    }
});

$("value").addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        putValue();
    }
});

// Retrieve: Enter performs GET
$("getKey").addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        getValue();
    }
});

// Delete: Enter performs DELETE
$("deleteKey").addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        deleteValue();
    }
});


// ==================== BUTTON EVENTS ====================

$("putButton").addEventListener("click", putValue);
$("getButton").addEventListener("click", getValue);
$("deleteButton").addEventListener("click", deleteValue);
$("countButton").addEventListener("click", getSize);
$("allButton").addEventListener("click", getAllValues);


// ==================== INITIALIZATION ====================

// Check immediately, then repeat every 5 seconds.
checkServer();
setInterval(checkServer, 5000);
