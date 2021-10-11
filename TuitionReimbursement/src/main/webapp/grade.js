Object.addEventListener("load", load);

function load() {
    let userCookie = JSON.parse(getCookie("employee"));
    let user = JSON.parse(userCookie);
    let cookie = JSON.parse(getCookie("requestList"));
    let list = JSON.parse(cookie);
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);

    if ((user.benCo === "true") || (ds === "true") || (dh === "true")) {
        let li = document.createElement("li");
        li.setAttribute("class", "nav-item");

        let a = document.createElement("a");
        a.setAttribute("class", "nav-link");
        a.setAttribute("href", "http://localhost:8080/TuitionReimbursment/approval.html");

        li.appendChild(a);

        let element = document.getElementById("navbar");
        element.appendChild(li);
    }

    for (let r in list) {
        //req = JSON.parse(r);
        let request = document.getElementById('request');
        if ((r.employeeId === user.employeeId) && ((r.benCoApproval === "true") || (r.dsApproval === "true") || (r.dhApproval === "true"))) {
            let option = document.createElement("option");
            option.innerHTML += '<option value="' + r.id + '">' + r.eventType + ' on ' + r.eventDate + '</option>'
            request.appendChild(option);
        }
    }
}

async function submitGrade() {
    let url = "http://localhost:8080/TuitionReimbursement/grade"

    let reqUpdate = {
        id: document.getElementById('request'),
        grade: document.getElementById('grade')
    }

    console.log(reqUpdate);

    let res = await fetch(url, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(reqUpdate)
    });

    let resJson = await res.text()
    .then(res => {
        console.log(res)
    })
    .catch(error => {
        console.log(error);
    })
}