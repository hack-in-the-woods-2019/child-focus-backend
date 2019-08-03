(function() {

    var dragAndDropHandler = {

        draggedElement: null,

        applyDragEvents: function(element){
            element.draggable = true;

            var dragAndDropHandler = this;

            element.addEventListener('dragstart', function(e) {
                dragAndDropHandler.draggedElement = e.target;
                e.dataTransfer.setData('text/plain', '');  //Firefox Specific
            });
        },

        applyDropEvents: function(dropper) {

            dropper.addEventListener('dragover', function(e) {
                e.preventDefault();
                this.classList.add('drop_hover');
            });

            dropper.addEventListener('dragleave', function (e) {
                this.classList.remove('drop_hover');
            });

            var dragAndDropHandler = this;

            dropper.addEventListener('drop', function(e){
               var target = e.target,
                   draggedElement = dragAndDropHandler.draggedElement,
                   clonedElement = draggedElement.cloneNode(true);

               while (target.className.indexOf('dropping-zone') == -1) {
                   target = target.parentNode;
               }

               target.classList.remove('drop_hover');

               clonedElement = target.appendChild(clonedElement);
               dragAndDropHandler.applyDragEvents(clonedElement);
               draggedElement.parentNode.removeChild(draggedElement);
            });
        }
    };

    var elements = document.querySelectorAll('.draggable'),
        elementsLen = elements.length;

    for (var i = 0; i < elementsLen; i++) {
        dragAndDropHandler.applyDragEvents(elements[i]);
    }

    var droppers = document.querySelectorAll('.dropping-zone'),
        droppersLen = droppers.length;

    for (var i = 0; i < droppersLen; i++) {
        dragAndDropHandler.applyDropEvents(droppers[i])
    }

})();