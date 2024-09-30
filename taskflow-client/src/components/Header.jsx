import React from 'react'
import Avatar from '@mui/material/Avatar';
import "../modules/Header.css"

function Header() {
  return (
    <div className='container z-10 sticky left-0 right-0 top-0 py-3 px-5 lg:px-10, flex justify-between items-center'>

        <p className='font-bold text-2xl ml-36'>Taskflow</p>

        <div className="flex items-center gap-5">
    
            <Avatar sx={{backgroundColor:"rgba(9,9,121,1)"}}>N</Avatar>
            <p>Neo</p>

        </div>
    </div>
  )
}

export default Header