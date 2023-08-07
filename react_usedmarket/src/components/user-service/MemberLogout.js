import React from 'react'

function MemberLogout() {


 
        localStorage.removeItem("jwt");
        window.location.href = "/login"
  


  return (
    <div>


    </div>
  )
}

export default MemberLogout