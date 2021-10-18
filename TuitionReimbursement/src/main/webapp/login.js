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
        window.location.replace("http://localhost:8080/TuitionReimbursement/menu.html");
    })
    .catch(error => {
        console.log(error);
    })
    
    console.log("flag 2");
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }