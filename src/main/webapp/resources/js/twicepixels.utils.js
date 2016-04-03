$tw = {
    ajax: function (settings) {
        var _defaults = {
            async: true,
            type: "POST",
            operation: "change"
        };
        var options = jQuery.extend({}, _defaults, settings);
        var refreshTarget = function (options, content) {
            if (content.length > 0) {
                var targetId = options.targetId;
                var selector = $.type(targetId) == "string" ?
                    document.getElementById(targetId) : targetId;
                if (options.operation == "change") {
                    $(selector).html(content);
                } else if (options.operation == "prepend") {
                    $(selector).prepend(content);
                } else if (options.operation == "append") {
                    $(selector).append(content);
                } else if (options.operation == "replace") {
                    $(selector).replaceWith(content);
                }
            }
        };
        if (options.form) {
            var _form = $(options.form);
            options.url = $(_form).attr('action');
            options.data = $(_form).serialize();
            options.success = function (response) {
                if (response.hasErrors) {
                    $.each(response.errors, function (index, error) {
                        var _pat = '[data-valmsg-for="' + error.field + '"]';
                        $(_form).find(_pat).html(error.message);
                    });
                } else if (options.targetId) {
                    refreshTarget(options, response);
                }
            }
        }
        $.post(options.url, options.data).done(options.success);
    }
};