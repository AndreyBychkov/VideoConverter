const submitButton = document.querySelector(".upload-form__submit-button");
const form = document.forms.uploader;
const downloadContainer = document.querySelector(".download-container");
const status = document.querySelector(".status");
let uploadFileName = document.querySelector(".upload-form__filename")

function renderInitialState() {
    status.style.display = "block";
    status.textContent = "Converting...";
    downloadContainer.style.display = "none";
    downloadContainer.classList.remove("download-container_animation");
}

async function submitButtonHandler (event) {
    event.preventDefault();

    let fileInput = form.elements["upload-form__input"].value;
    if (fileInput === "") {
        alert("Video-file is not chosen!");
        return;
    }

    const formData = new FormData(form);
    let convertingFormat = form.elements.selector.value;
    let fileName  = formData.get("initial-document").name.split(".")[0];

    renderInitialState();

    let response = await fetch(`/convert/${convertingFormat}`, {
        method: 'POST',
        body: new FormData(form)
    });

    if (response.ok) {
        let result = await response.blob();
        status.style.display = "none";
        downloadContainer.style.display = "flex";
        downloadContainer.classList.add("download-container_animation");
        uploadFileName.textContent = "choose file";
        form.reset();

        let file = new File([result],{type: `video/${convertingFormat}`});
        const downloadButton = document.querySelector(".download-container__link");
        downloadButton.href = URL.createObjectURL(file);
        downloadButton.download = `${fileName}.${convertingFormat}`;
    } else {
        uploadFileName.textContent = "choose file";
        form.reset();
        status.textContent = `Server Error: ${response.status}`;
    }
}

submitButton.addEventListener('click', submitButtonHandler);