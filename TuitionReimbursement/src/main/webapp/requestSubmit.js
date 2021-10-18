document.addEventListener("load", getRequests());
let list;

function load() {
    
    let benCoCookie = JSON.parse(getCookie("benCo"));
    let benCo = JSON.parse(benCoCookie)
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);

    if ((benCo == true) || (ds == true) || (dh == true)) {
        console.log("flag Approval");
        let li = document.createElement("li");
        let element = document.getElementById("navbar");
        li.innerHTML += '<li class="nav-item"><a class="nav-link" href="http://localhost:8080/TuitionReimbursement/approval.html">Approve Request</a></li>'
        element.appendChild(li);
    }
}

async function submit() {
    let userIdCookie = JSON.parse(getCookie("employeeId"));
    let userId = JSON.parse(userIdCookie);

    console.log(userId);
    
    let url = "http://localhost:8080/TuitionReimbursement/requests";

    let req = {
        employeeId: userId,
        amount: document.getElementById('amount').value,
        eventType: document.getElementById('eventType').value,
        eventDate: document.getElementById('eventDate').value,
        eventTime: document.getElementById('eventTime').value,
        location: document.getElementById('eventLocation').value,
        description: document.getElementById('description').value,
        gradingFormat: document.getElementById('gradingFormat').value,
        justification: document.getElementById('justification').value,
        urgent: document.getElementById('urgent').checked
    }

    console.log(req);

    let res = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(req)
    });

    let resJson = await res.text()
    .then (res => {
        console.log(res);
        window.location.replace("http://localhost:8080/TuitionReimbursement/menu.html");
    })
    .catch( error => {
        console.log(error);
    })
}

async function getRequests() {
    let url = "http://localhost:8080/TuitionReimbursement/requests"

    let res = await fetch(url, {
        method: "GET",
        headers: {"Content-Type": "application/json"}
    }).then(res => res.json())
    .then(data => {
        console.log(data);
        list = data;
        console.log(list);
    });

    load();
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