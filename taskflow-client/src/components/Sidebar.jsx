import React, { useState } from 'react'
import "../modules/Sidebar.css"
import CreateTask from './CreateTask';


const menu = [
    {name:"Home", value:"HOME"},
    {name:"Completed", value:"COMPLETED"},
    {name:"Notification", value:"NOTIFICATION"},
    {name:"Create New Task"}
]

function Sidebar() {

 const [activeMenu, setActiveMenu] = useState("Home");

 const [openCreateTaskForm, setOpenCreateTaskForm] = useState(false);
 const handleCloseCreateTaskForm = () => {
  setOpenCreateTaskForm(false);
 }
 const handleOpenCreateTaskForm = () => {
  handleOpenCreateTaskForm(true)
 }

 const handleMenuChange=(item=>{
    setActiveMenu(item.name)
 })

 if(item.name == "Create New Task"){
  handleOpenCreateTaskForm();
 }

  return (
 <>
    <div className='card min-h-[85vh] flex flex-col justify-center fixed w-[20vw]'>
        <div className="space-y-5">
           {
            menu.map((item)=> <p key={item.name} onClick={()=>handleMenuChange(item)} className={`py-3 px-5 text-center cursor-pointer ${activeMenu === item.name? "activeMenuItem":"menuItem"}`}>
            {item.name}
           </p>)
           }
        </div>

    </div>

    <CreateTask open={openCreateTaskForm} handleClose = {handleCloseCreateTaskForm}/>
 </>
  )
}

export default Sidebar