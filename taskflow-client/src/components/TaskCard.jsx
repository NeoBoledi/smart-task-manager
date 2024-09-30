import React from 'react'
import "../modules/TaskCard.css"
import { Menu, MenuItem } from '@mui/material';

function TaskCard() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const openMenu = Boolean(anchorEl);
  const handleMenuClick = (event)=>{
    setAnchorEl(event.currentTarget);
  }
 
  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  return (
    <div className="card lg:flex justify-between">
      <div className="lg:flex gap-5 items-center space-y-2 w-[90%] lg:w-[70%]">
        <div className="space-y-5">
          <div className="space-y-2">
            <h3 className="font-bold text-lg">Car Rental Website</h3>
            <p className="text-gray-500 text-sm">We are heare to create this</p>

            <div className="btn flex justify-between">
            <button className='btn-edit'
             id="basic-button"
             aria-controls={openMenu ? 'basic-menu' : undefined}
             aria-haspopup="true"
             aria-expanded={openMenu ? 'true' : undefined}
             onClick={handleMenuClick}
            >Edit</button>
            <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={openMenu}
        onClose={handleMenuClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        <MenuItem onClick={handleMenuClose}>Profile</MenuItem>
        <MenuItem onClick={handleMenuClose}>My account</MenuItem>
        <MenuItem onClick={handleMenuClose}>Logout</MenuItem>
      </Menu>

            <button className='btn-delete'>Delete</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default TaskCard