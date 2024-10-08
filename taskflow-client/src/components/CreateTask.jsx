import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

function CreateTask() {
    const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState({
    title:" ",
    description:" ",
    deadline: new Date(),
    completed: false, 
})

const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  
  return (
    <div>
      <Button onClick={handleOpen}>Open modal</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
         <Grid container spacing = {2} alignItems="center">
            <Grid item xs={12}>
                <TextField
                label = "Title" fullwidth name="title value={formData.title}"
                />
            </Grid>
         </Grid>
        </Box>
      </Modal>
    </div>
  );
}

export default  CreateTask