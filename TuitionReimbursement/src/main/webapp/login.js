async function login() {
    let url = "http://localhost:8080/TuitionReimbursement/login";

    let user = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    }

    console.log(user);

    console.log('flag 0');
    let res = await fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(user)
    });

    console.log("flag 1");
    let resJson = await res.text()
    .then(res => {
        console.log(res);
        
    })
    .catch(error => {
        console.log(error);
    })
    
    console.log("flag");
}