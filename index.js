function Search(formSearch) {
    var formElement = document.querySelector(formSearch);
    if (formElement) {

        var formInput = document.querySelector('.header__seach-input');

        if (formInput) {
            formInput.onclick = showHistory;
        }

        var formUL;

        function showHistory(event) {
            var formHistory = formElement.querySelector('.header_seach-history');
            formHistory.style.display = 'block';
            if (formHistory) {

                formUL = formElement.querySelectorAll('.header__seach-history-item');

                // for (var formLi of formUL) {
                //     formLi.innerHTML = "alo 1 2 3 4 5";
                // }

                formUL[0].innerHTML = "giày thể thao name";
                formUL[1].innerHTML = "giày thể thao nữ";
                formUL[2].innerHTML = "gậy đánh golf";
                for (var formLi of formUL) {
                    formLi.onclick = solverSearch;
                }

                function solverSearch(event) {
                    formInput.value = event.target.innerHTML;
                    formHistory.style.display = 'none';
                    formUL.style.color = '#333';
                }
            }

            formInput.oninput = noneHistory;
        }

        function noneHistory(event) {

            var formHistory = formElement.querySelector('.header_seach-history');
            if (formHistory) {
                var i = 0;
                for (var formLi of formUL) {
                    var s = '' + formLi.innerHTML;
                    if (!s.includes('' + event.target.value)) {
                        ++i;
                        formLi.style.color = '#333';

                    }
                    if (s.includes('' + event.target.value)) {
                        formLi.style.color = '#7488a1';
                        formHistory.style.display = 'block';

                    }
                }

                if (i == formUL.length) {
                    formHistory.style.display = 'none';
                }

            }
            if (formInput.value == '') {
                formHistory.style.display = 'block';
                for (var formLi of formUL) {
                    formLi.style.color = '#333';
                }
            }
        }
    }


    formElement.onsubmit = function(event) {
        formElement.submit();
    }
}


function Active(formFilter) {
    var element = document.querySelector(formFilter);
    var formElement = element.querySelectorAll('.btn');
    var childFilter;

    function getParent(element, selector) {

        while (element.parentElement) {
            if (element.parentElement.matches(selector)) {
                return element.parentElement;
            }
            element = element.parentElement;
        }
    }
    if (formElement) {

        for (childFilter of formElement) {
            childFilter.onclick = nowActive;
        }


        function nowActive(event) {
            event.preventDefault();
            for (var childFilter1 of formElement) {
                if (childFilter1.classList.contains('btn--primary')) {
                    childFilter1.classList.remove('btn--primary');
                    childFilter1.classList.add('btn-1');
                }
            }
            // var parentElement = getParent(event.target, '.btn');
            // if (parentElement) {
            //     parentElement.classList.remove('btn-1');
            //     parentElement.classList.add('btn--primary');
            // }
            event.target.classList.remove('btn-1');
            event.target.classList.add('btn--primary');

        }


    }

    var categoryElement = document.querySelectorAll('.category-item');
    for (var categoryLi of categoryElement) {
        categoryLi.onclick = active;
    }

    function active(event) {
        event.preventDefault();
        for (var categoryLi of categoryElement) {
            if (categoryLi.classList.contains('category-item-active')) {
                categoryLi.classList.remove('category-item-active');
            }
        }
        var parentcategoryLi = getParent(event.target, '.category-item')
        if (parentcategoryLi) {
            parentcategoryLi.classList.add('category-item-active');
        }
    }
    // console.log(formElement);
}

function Cart(selector) {
    var cartElement = document.querySelector(selector);
    var cartElementLi = cartElement.querySelectorAll('.header_cart-item');
    var count = 0;
    var bool = false;
    if (bool) {
        document.querySelector('.header__cart-list-no-cart-msg').style.display = 'flex';
        for (var Li of cartElementLi) {
            Li.style.display = 'none';
        }
        cartElement.querySelector('.header_cart-heading').style.display = 'none';
        cartElement.querySelector('.header_cart-view-cart').style.display = 'none';
    } else {
        document.querySelector('.header__cart-list-no-cart-msg').style.display = 'none';
        for (var Li of cartElementLi) {
            Li.style.display = 'flex';
        }

        function getParent(element, selector) {

            while (element.parentElement) {
                if (element.parentElement.matches(selector)) {
                    return element.parentElement;
                }
                element = element.parentElement;
            }
        }

        for (var Li of cartElementLi) {
            var removeLi = Li.querySelector('.header_cart-item-remove')
            removeLi.onclick = remove;
        }

        function remove(event) {
            var parentLi = getParent(event.target, '.header_cart-item')
            if (parentLi) {
                parentLi.style.display = 'none'
                count += 1;
                if (count == cartElementLi.length) {
                    document.querySelector('.header__cart-list-no-cart-msg').style.display = 'flex';
                    cartElement.querySelector('.header_cart-heading').style.display = 'none';
                    cartElement.querySelector('.header_cart-view-cart').style.display = 'none';
                }
            }
        }
    }
}

function Category(selector) {
    var categoryElement = document.querySelector(selector);

    if (categoryElement) {
        categoryElement.onmouseenter = solve;
        categoryElement.onmouseleave = solve2;
    }

    function solve(event) {
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

    function solve2(event) {
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
                    var listImg = document.querySelectorAll(".img-item-img");
                    for (var img of listImg) {
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
                    }
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

    function menu(event) {
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