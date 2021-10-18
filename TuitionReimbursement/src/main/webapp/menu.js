async function getRequests() {
    let url = "http://localhost:8080/TuitionReimbursement/menu"

    let res = await fetch(url, {
        method: "GET",
        headers: {"Content-Type": "application/json"}
    }).then(res => res.json())
    .then(data => {
        console.log(data);
        list = data;
        console.log(list);
    });

    createList();
}

document.addEventListener("load", getRequests());
let list;


function createList() {
    console.log(getCookie("employeeId"));
    let userIdCookie = JSON.parse(getCookie("employeeId"));
    let userId = JSON.parse(userIdCookie);
    let benCoCookie = JSON.parse(getCookie("benCo"));
    let benCo = JSON.parse(benCoCookie)
    let dsCookie = JSON.parse(getCookie("directSupervisor"));
    let ds = JSON.parse(dsCookie);
    let dhCookie = JSON.parse(getCookie("departmentHead"));
    let dh = JSON.parse(dhCookie);
    
    for (let i = 0; i < list.length; i++) {
        let r = list[i];
        console.log(r);
        if (r.employeeId === userId) {
            let row = document.createElement("div");
            row.setAttribute("class", "row");

            let approved;
            if ((r.dsApproval == true) && (r.dhApproval == true) && (r.benCoApproval == true)) {
                approved = "Approved";
            } 

            else if (r.rejected) {
                approved = "Rejected";
            }

            else {
                approved = "Pending";
            }

            let grade;
            if (r.grade === null) {
                grade = "N/A";
            }

            else {
                grade = r.grade;
            }
           
            console.log(approved);
            row.innerHTML += '<div class="col">' + r.eventType + '</div><div class="col">' + r.eventDate + '</div><div class="col">' + grade + '</div><div class="col">' + r.amount + '</div><div class="col">' + approved + '</div>';

            let element = document.getElementById("table");
            element.appendChild(row);
        }

    }

    
    if ((benCo == true) || (ds == true) || (dh == true)) {
        console.log("flag Approval");
        let li = document.createElement("li");
        let element = document.getElementById("navbar");
        li.innerHTML += '<li class="nav-item"><a class="nav-link" href="http://localhost:8080/TuitionReimbursement/approval.html">Approve Request</a></li>'
        element.appendChild(li);
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