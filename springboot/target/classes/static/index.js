function ShowIndex(selectorOne,selectorTwo){
	
		var check = 0;
        var checkCategory=0;
        document.querySelector(selectorOne).onclick = solveCategory;
		document.querySelector(selectorTwo).onclick = solve;
        function solve() {
            if(checkCategory===1){
                document.querySelector(".grid__column-2-1 ").style.display = "none ";
                checkCategory=0;
            }
            var list = document.querySelectorAll(".home-heading-btn ");
            if (check === 0) {
                for (var items of list) {
                    items.style.height = '30px';
                }
                check = 1;
            } else {
                for (var items of list) {
                    items.style.height = "0px ";
                }
                check = 0;
            }
        };
        window.onresize = function(){
            var screenX = window.innerWidth;
            if(screenX<700){
                var list = document.querySelectorAll(".home-heading-btn ");
                for (var items of list) {
                    items.style.height = "0px ";
                }
            }
            var list = document.querySelectorAll(".home-heading-btn ");
            if (screenX>700) {
                for (var items of list) {
                    items.style.height = "40px ";
                }
                document.querySelector(".grid__column-2-1 ").style.display = "none ";
                document.querySelector(".grid__column-2-1 ").style.width = "24% ";
            }
        }
        function solveCategory() {
            var screenX = window.innerWidth;
            if(screenX<700){
                if(check===1){
                    var list = document.querySelectorAll(".home-heading-btn ");
                    for (var items of list) {
                        items.style.height = "0px ";
                    }
                    check = 0;
                }

                if (checkCategory===0){
                    document.querySelector(".grid__column-2-1 ").style.display = "flex ";
                    var x = window.innerWidth-15;
                    document.querySelector(".grid__column-2-1 ").style.width = x.toString() + "px ";
                    checkCategory=1;
                }
                else{
                    document.querySelector(".grid__column-2-1 ").style.display = "none ";
                    checkCategory=0;
                }
            }
        }
}		





function Category(selector) {
    var categoryElement = document.querySelector(selector);

    if (categoryElement) {
        categoryElement.onmouseenter = solve;
        categoryElement.onmouseleave = solveTwo;
    }

    function solve() {
        var category = document.querySelector('.grid__column-2-1');
        var screenX = window.innerWidth;
        if (category && screenX > 700) {
            category.style.display = 'block'
            var containElement = document.querySelector('.container');
            if (containElement) {
                containElement.querySelector('.modal_black').style.display = 'block';
            }
        }
    }

    function solveTwo() {
        var category = document.querySelector('.grid__column-2-1');
        var screenX = window.innerWidth;
        if (category && screenX > 700) {
            category.style.display = 'none'
            var containElement = document.querySelector('.container');
            if (containElement) {
                containElement.querySelector('.modal_black').style.display = 'none';
            }
        }
    }
}

function ViewImage(selector) {
    var imageElenment = document.querySelector(selector);
    var i = 1,
        tranX;
    if (imageElenment) {
        imageBig = imageElenment.querySelector('.img-item-tmp');
        imageList = imageElenment.querySelectorAll('.img-list1-item');
        if (imageBig && imageList) {
            for (var image of imageList) {
                var imageSmall = image.querySelector('.img-item-img-item');
                imageSmall.onclick = view;
            }
        }

        function view(event) {
            var index = 1;
            for (var image of imageList) {
                if (image.querySelector('.img-item-img-item').src === event.target.src) {
                    tranX = i - index;
                    imageBig.style.transform = "translateX(" + tranX + "00%)";
                    imageBig.style.transition = "all " + 0.5 * 2 / (index + 1) + "s ease-in-out";
                } else {
                    index += 1;
                   // var listImg = document.querySelectorAll(".img-item-img");
                   // for (var img of listImg) {
                        // if (img.src === event.target.src) {
                        //     img.style.opacity = '1';
                        // } else {
                        //     img.style.opacity = '0';

                        // }
                        // if (img.src === event.target.src) {
                        //     img.style.display = 'block';
                        // } else {
                        //     img.style.display = 'none';

                        // }
                    //}
                }
            }
        }
    }
}

function ChooseSize(selector) {
    var chooseElement = document.querySelector(selector);
    if (chooseElement) {
        var sizeElement = chooseElement.querySelectorAll('.product-buy-choice-size-number');
        for (var size of sizeElement) {
            size.onclick = choose;
        }

        function choose(event) {
            for (var size of sizeElement) {
                size.style.border = "1px solid var(--border-color)";
            }
            event.target.style.border = '2px solid var(--black-color)';
            document.querySelector(".btn--primary2").style.background = 'var(--primary-color)';

        }


        var subElement = chooseElement.querySelector('.btn--primary2');
        subElement.onclick = buyProduce;

        function buyProduce(event) {
            var color = document.querySelector(".btn--primary2").style.background;
            if (color == "") {
                event.preventDefault();
            }
        }
    }
}

function Sticky(selector) {
    var stickyElement = document.querySelector(selector);
    if (stickyElement) {
        var offsetTop = stickyElement.offsetTop;
        document.addEventListener('scroll', onscrollY);

        function onscrollY() {
            var screenX = window.innerWidth;
            if (window.scrollY >= offsetTop && screenX > 700) {
                stickyElement.classList.add('sticky')
            } else {
                stickyElement.classList.remove('sticky');
            }
        }
    }
}

function Menu(selector) {
    var x = document.querySelectorAll(selector);
    var y = x[1];
    y.onclick = menu;

    function menu() {
        var z = document.querySelector('.home__heading-a');

        if (z.style.display === "block") {
            z.style.display = "none";
        } else {
            z.style.display = "block";
        }
    }
}

// function movePage(event){

//     document.addEventListener('mouseup', printMousePos);
//     var x,y;
//     function printMousePos(e){

//         x = e.pageX;
//         y= e.pageY;
//         document.querySelector(".editEmployeeModal ").style.left = x + 'px';
//         document.querySelector(".editEmployeeModal ").style.top = y + 'px';
//     }
// }


function manager(selector, add, edit) {
    var selectorElement = document.querySelector(selector);
    var list = selectorElement.querySelectorAll('.button-item');
    list[0].onclick = addProduct;
    list[1].onclick = editProduct;
    list[2].onclick = removeProduct;

    function addProduct(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search ").style.display = 'none';
            document.querySelector(".editEmployeeModal ").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'none';
            document.querySelector('.modal-title').innerHTML = add;
            document.querySelector('.btn-product').value = "Add";
        }

        var cancel = document.querySelector('.close');
        cancel.onclick = function() {
            document.querySelector(".editEmployeeModal ").style.display = 'none';
            var list = selectorElement.querySelectorAll('.button-item');
            for (var item of list) {
                item.style.backgroundColor = 'var(--primary-color)';
            }
        }
    }

    function editProduct(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search ").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'block';
            document.querySelectorAll(".cart-buttton-update ")[0].style.display = 'block';
            document.querySelectorAll(".cart-buttton-update ")[1].innerHTML = "Cập nhật ";
            document.querySelector('.cart-button-update-nth').style = 'display:flex; justify-content: space-between';
            var list = document.querySelectorAll('.checkbox');
            for (var item of list) {
                item.style.display = 'none';
            }
            list = document.querySelectorAll('.edit');
            for (var item of list) {
                item.style.display = 'block';
            }
            document.querySelector('.modal-title').innerHTML = edit;
            document.querySelector('.btn-product').value = "Edit ";
        }

        var cancel = document.querySelector('.close');
        cancel.onclick = function() {
            document.querySelector(".editEmployeeModal ").style.display = 'none';
        }

        var listButton = document.querySelectorAll('.cart-buttton-update');
        listButton[0].onclick = Cancel;
        listButton[1].onclick = Update;

        function Cancel() {
            if (confirm("Bạn muốn hủy cập nhật")) {
                document.getElementById("search ").style.display = 'none';
                document.querySelector('.cart-detail').style.display = 'none';
                var list = selectorElement.querySelectorAll('.button-item');
                for (var item of list) {
                    item.style.backgroundColor = 'var(--primary-color)';
                }
            }
        }

        function Update() {
            alert("Cập nhật thành công");
            document.getElementById("search ").style.display = 'none';
            document.querySelector('.cart-detail').style.display = 'none';
            var list = selectorElement.querySelectorAll('.button-item');
            for (var item of list) {
                item.style.backgroundColor = 'var(--primary-color)';
            }
        }
    }

    function removeProduct(event) {
        if (event.target.style.backgroundColor == 'var(--primary-color)') {
            var list = selectorElement.querySelectorAll('.button-item');

            for (var item of list) {
                if (item != event.target) {
                    item.style.backgroundColor = '#999';
                }
            }
            document.getElementById("search ").style.display = 'block';
            document.querySelector('.cart-detail').style.display = 'block';
            document.querySelectorAll(".cart-buttton-update ")[1].innerHTML = "Xóa tất cả ";
            document.querySelectorAll(".cart-buttton-update ")[0].style.display = 'none';
            document.querySelector('.cart-button-update-nth').style = 'display:flex; justify-content: flex-end';
            var list = document.querySelectorAll('.edit');
            for (var item of list) {
                item.style.display = 'none';
            }
            list = document.querySelectorAll('.checkbox');
            for (var item of list) {
                item.style.display = 'block';
            }
            var listButton = document.querySelectorAll('.cart-buttton-update');
            listButton[1].onclick = Delete;

            function Delete() {
                if (confirm("Bạn chắc chắn xóa sản phẩm")) {
                    alert("Xóa thành công");
                    document.getElementById("search ").style.display = 'none';
                    document.querySelector('.cart-detail').style.display = 'none';
                    var list = selectorElement.querySelectorAll('.button-item');
                    for (var item of list) {
                        item.style.backgroundColor = 'var(--primary-color)';
                    }
                }
            }
        }
    }
}