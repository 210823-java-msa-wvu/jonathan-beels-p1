document.addEventListener("load", getRequests());
let list;

function load() {
    let userIdCookie = JSON.parse(getCookie("employeeId"));
    let userId = JSON.parse(userIdCookie);
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

    for (let i = 0; i < list.length; i++) {
        let r = list[i];
        console.log(r);
        //req = JSON.parse(r);
        let request = document.getElementById('request');
        if ((r.employeeId === userId)) {
            let option = document.createElement("option");
            option.setAttribute("value", r.id);
            option.innerHTML += '<option value="' + r.id + '">' + r.eventType + ' on ' + r.eventDate + '</option>'
            request.appendChild(option);
        }
    }
}

async function submitGrade() {
    let userIdCookie = JSON.parse(getCookie("employeeId"));
    let userId = JSON.parse(userIdCookie);

    let url = "http://localhost:8080/TuitionReimbursement/requests"

    let reqUpdate = {
        id: document.getElementById('request').value,
        employeeId: userId,
        eventType: document.getElementById('eventType').value,
        amount: document.getElementById('amount').value,
        eventDate: document.getElementById('eventDate').value,
        eventTime: document.getElementById('eventTime').value,
        location: document.getElementById('eventLocation').value,
        gradingFormat: document.getElementById('gradingFormat').value,
        description: document.getElementById('description').value,
        justification: document.getElementById('justification').value,
        urgent: document.getElementById('urgent').checked
    }

    console.log(reqUpdate);

    let res = await fetch(url, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(reqUpdate)
    });

    let resJson = await res.text()
    .then(res => {
        console.log(res);
        window.location.replace("http://localhost:8080/TuitionReimbursement/menu.html");
    })
    .catch(error => {
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

function fill() {
    let id = document.getElementById("request").value;
    console.log(id);

    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            let r = list[i];

            document.getElementById("eventType").setAttribute("value", r.eventType);
            document.getElementById("amount").setAttribute("value", r.amount);
            document.getElementById("eventDate").value = r.eventDate;
            document.getElementById("eventTime").value = r.eventTime;
            document.getElementById("eventLocation").value = r.location;
            document.getElementById("gradingFormat").value = r.gradingFormat;
            document.getElementById("description").value = r.description;
            document.getElementById("justification").value = r.justification;
            document.getElementById("urgent").checked = r.urgent;
        }
    }


}