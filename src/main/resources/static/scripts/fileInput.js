const formInput = document.querySelector("#upload-form__input");
const fileName = document.querySelector(".upload-form__filename");

formInput.addEventListener("change", () => {
    let filename = formInput.files.item(0).name;
    fileName.textContent = filename;
});

// TODO Prevent default on submit button and clear filename on submit