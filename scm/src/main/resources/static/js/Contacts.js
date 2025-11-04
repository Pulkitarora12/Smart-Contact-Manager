console.log("contacts");
const baseUrl = "localhost:8080";
const viewContactModal = document.getElementById('view_contact_modal');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions
)

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {

    console.log(id);

    let data;

    try {
        data = await (
            await fetch(`http://${baseUrl}/api/contacts/${id}`)
        ).json();
        console.log(data);
        console.log(data.name);

        document.querySelector('#contact_name').innerHTML = data.name;
        document.querySelector('#contact_email').innerHTML = data.email;
        openContactModal();
    } catch (error) {
        console.log("Error : ", error);
    }
}

//delete contact
function deleteContact(id) {
    Swal.fire({
        title: "Do you want to deltete the contact?",
        showCancelButton: true,
        confirmButtonText: "Delete",
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url = `http://${baseUrl}/user/contact/delete/` + id;
            window.location.replace(url);
        }
    });
}