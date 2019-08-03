var i = 1;
const MaxNumberOfArtistActivities = 5;
var artistActivitiesDescriptionArray = new Array(MaxNumberOfArtistActivities);

$("#add_activity").click(function () {
    if (i < MaxNumberOfArtistActivities) {
        var descriptionToReload = "artistActivityDescription" + i;
        if(artistActivitiesDescriptionArray[i] !== undefined) {
            document.getElementById(descriptionToReload).value = artistActivitiesDescriptionArray[i];
        }
        document.getElementById(descriptionToReload).setAttribute("required", "required");
        var activityToModify = "artistActivity" + i;
        document.getElementById(activityToModify).classList.remove("d-none");
        i += 1;
    }
    if (i < 5) {
        document.getElementById("del_activity").classList.remove("d-none");
    } else if (i === 5) {
        document.getElementById("add_activity").classList.add("d-none");
    }
});

$("#del_activity").click(function () {
    if (i > 1) {
        i -= 1;
        var activityToModify = "artistActivity" + i;
        document.getElementById(activityToModify).classList.add("d-none");
        var descriptionToDelete = "artistActivityDescription" + i;
        artistActivitiesDescriptionArray[i] = document.getElementById(descriptionToDelete).value;
        document.getElementById(descriptionToDelete).value = "";
        document.getElementById(descriptionToDelete).removeAttribute("required");
    }
    if (i === 1) {
        document.getElementById("del_activity").classList.add("d-none");
    } else if (i < MaxNumberOfArtistActivities) {
        document.getElementById("add_activity").classList.remove("d-none");
    }
});