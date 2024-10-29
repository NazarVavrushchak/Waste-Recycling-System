document.getElementById("signUpForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/userRegistration/signUp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email, password }),
    });

    if (response.ok) {
        alert("Registration successful!");
        window.location.href = "signin.html";
    } else {
        const error = await response.text();
        alert("Registration failed: " + error);
    }
});

document.getElementById("googleSignUp").addEventListener("click", function () {
    window.location.href = "/oauth2/authorization/google";
});
