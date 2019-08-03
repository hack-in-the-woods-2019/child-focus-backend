$(document).ready(function() {
    $("#sortTable").DataTable({
        columnDefs : [
            { type : 'artistCard.lastUpdate', targets : [3] }
        ],
    });
});