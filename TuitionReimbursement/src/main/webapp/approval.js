document.addEventListener("load", getRequests());

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
    } else {
        window.location.replace("http://localhost:8080/TuitionReimbursement/menu.html");
    }

    for (let i = 0; i < list.length; i++) {
        let r = list[i];
        //req = JSON.parse(r);
        let request = document.getElementById('request');
        console.log(r.id);
        console.log(!(r.employeeId === userId));
        console.log(r.dsApproval);
        console.log(r.dhApproval);
        if (!(r.employeeId === userId) && ((r.benCoApproval == false) || (r.dsApproval == false) || (r.dhApproval == false)) && !(r.rejected)) {
            console.log(r.id);
            let option = document.createElement("option");
            option.setAttribute("value", r.id);
            option.innerHTML += '<option value="' + r.id + '">' + r.eventType + ' on ' + r.eventDate + '</option>'
            request.appendChild(option);
        }0
    }
}

async function approve() {
    let userIdCookie = JSON.parse(getCookie("employeeId"));
    let userId = JSON.parse(userIdCookie);
    let benCoCookie = JSON.parse(getCookie("benCo"));
    let benCo = JSON.parse(benCoCookie)
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);
    let url = "http://localhost:8080/TuitionReimbursement/approval"

    let reqUpdate = null;
    if (benCo == true) {
        reqUpdate = {
            id: document.getElementById('request').value,
            benCoApproval: true
        }
    }

    if (ds == true) {
        reqUpdate = {
            id: document.getElementById('request').value,
            dsApproval: true
        }
    }

    if (dh == true) {
        reqUpdate = {
            id: document.getElementById('request').value,
            dhApproval: true
        }
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

async function reject() {
    let benCoCookie = JSON.parse(getCookie("benCo"));
    let benCo = JSON.parse(benCoCookie)
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);

    let url = "http://localhost:8080/TuitionReimbursement/approval"

    let reqUpdate = null;
    if (benCo || ds || dh) {
        reqUpdate = {
            id: document.getElementById('request').value,
            rejected: true
        }
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

async function requestInfo() {
    let benCoCookie = JSON.parse(getCookie("benCo"));
    let benCo = JSON.parse(benCoCookie)
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);

    let url = "http://localhost:8080/TuitionReimbursement/approval"

    let reqUpdate = null;
    if (benCo || ds || dh) {
        reqUpdate = {
            id: document.getElementById('request').value,
            additionalInfo: true
        }
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

function loadRequest() {
    let id = document.getElementById('request').value;

    for (let i = 0; i < list.length; i++) {
        let r = list[i];
        if (r.id == id) {
            

            let grade;
            if (r.grade === null) {
                grade = "N/A";
            }

            else {
                grade = r.grade;
            }
            let element = document.getElementById('display');
            
            element.innerHTML = '<div id="display" class="container">'
            + '<div class="row"><div class="col">Urgent</div><div class="col">' + ((r.urgent) ? "Yes" : "No") + '</div></div>'
            + '<div class="row"><div class="col">Event Type</div><div class="col">' + r.eventType + '</div></div>'
            + '<div class="row"><div class="col">Event Date</div><div class="col">' + r.eventDate + '</div></div>'
            + '<div class="row"><div class="col">Event Location</div><div class="col">' + r.location + '</div></div>'
            + '<div class="row"><div class="col">Amount</div><div class="col">' + r.amount + '</div></div>' 
            + '<div class="row"><div class="col">Description</div><div class="col">' + r.description + '</div></div>'
            + '<div class="row"><div class="col">Justification</div><div class="col">' + r.justification + '</div></div>'
            + '<div class="row"><div class="col">Grading Format</div><div class="col">' + r.gradingFormat + '</div></div>'
            + '<div class="row"><div class="col">Grade</div><div class="col">' + grade + '</div></div>'
            + '<div class="row"><div class="col">Benefits Coordinator Approval</div><div class="col">' + ((r.benCoApproval) ? "Yes" : "No") + '</div></div>'
            + '<div class="row"><div class="col">Department Head Approval</div><div class="col">' + ((r.dhApproval) ? "Yes" : "No") + '</div></div>'
            + '<div class="row"><div class="col">Direct Supervisor Approval</div><div class="col">' + ((r.dsApproval) ? "Yes" : "No") + '</div></div>'
            + '</div>';
            
        }
    }
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

  async function getRequests() {
    let url = "http://localhost:8080/TuitionReimbursement/approval"

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