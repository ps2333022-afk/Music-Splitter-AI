const audioFile = document.getElementById("audioFile");
const fileName = document.getElementById("fileName");
const splitBtn = document.getElementById("splitBtn");
const progressBar = document.getElementById("progressBar");
const status = document.getElementById("status");


audioFile.addEventListener("change", function () {

    const file = this.files[0];

    if (file) {
        fileName.innerHTML = "Selected: " + file.name;
        status.innerHTML = "Ready to upload 🎧";
    }

});


splitBtn.addEventListener("click", async function () {

    const file = audioFile.files[0];

    if (!file) {
        alert("Please select an audio file first");
        return;
    }


    const formData = new FormData();
    formData.append("file", file);


    status.innerHTML = "Uploading and processing... ⏳";
    progressBar.style.width = "50%";


    try {

        const response = await fetch(
            "http://localhost:8080/api/upload",
            {
                method: "POST",
                body: formData
            }
        );


        const result = await response.json();


        if(result.success){

            status.innerHTML =
            "✅ " + result.message;


            progressBar.style.width = "100%";


            const song = result.songName;


            status.innerHTML += `

            <br><br>

            <a href="http://localhost:8080/api/download/${song}/vocals">
            🎤 Download Vocals
            </a>

            <br>

            <a href="http://localhost:8080/api/download/${song}/drums">
            🥁 Download Drums
            </a>

            <br>

            <a href="http://localhost:8080/api/download/${song}/bass">
            🎸 Download Bass
            </a>

            <br>

            <a href="http://localhost:8080/api/download/${song}/other">
            🎵 Download Other
            </a>

            `;


        }else{

            status.innerHTML =
            "❌ " + result.message;

        }


    } catch(error){

        console.log(error);

        status.innerHTML =
        "Upload failed ❌";

    }

});