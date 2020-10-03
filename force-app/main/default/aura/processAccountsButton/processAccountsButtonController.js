({
    close: function (cmp, evt, hlp) {
        $A.get("e.force:closeQuickAction").fire();
        $A.get("e.force:refreshView").fire();
    }
});