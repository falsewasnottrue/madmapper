$(document).ready(function() {
    // folding of editors
    $("label[id^='lbl']").click(function() {
        var field = $(this).attr('for')
        $("div[id='edit_" + field + "']").toggleClass('collapsed');
    });

    // fold mappings
    $("input[id^='direct_']").click(function() {
        var field = $(this).attr('data-fieldname')
        $("div[id='mapping_" + field + "']").toggleClass('collapsed');
    })

    // add mapping
    $("button[id='addmap']").click(function() {
        var field = $(this).attr('data-fieldname');
        var key = $("input[id='key_" + field + "']").val();
        var val = $("input[id='val_" + field + "']").val();
        if (key && val) {
            var row = '<tr>' +
                '<td><input type="text" name="mapping_' + field + '_key_' + key + '" value="' + key + '"></td>' +
                '<td><input type="text" name="mapping_' + field + '_val_' + key + '" value="' + val + '"></td>' +
            '</tr>';

            $("#mapping_" + field + " > table tr:last").after(row);
            $("input[id='key_" + field + "']").val("");
            $("input[id='val_" + field + "']").val("");
        }
    });
});