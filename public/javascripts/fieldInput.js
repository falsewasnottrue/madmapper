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
            var row = '<tr><<td>&ldquo;' + key + '&rdquo;</td><td>&ldquo;' + val + '&rdquo;</td></tr>';
            $("#mapping_" + field + " > table tr:last").after(row);
            $("input[id='key_" + field + "']").val("");
            $("input[id='val_" + field + "']").val("");
        }
    });
});