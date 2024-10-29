document.getElementById("signInForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/userRegistration/signIn", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    if (response.ok) {
        alert("Sign-in successful!");
        window.location.href = "dashboard.html";
    } else {
        const error = await response.text();
        alert("Sign-in failed: " + error);
    }
});

document.getElementById("googleSignIn").addEventListener("click", function () {
    window.location.href = "/oauth2/authorization/google";
});
