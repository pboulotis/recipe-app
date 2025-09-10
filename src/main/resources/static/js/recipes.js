document.addEventListener("DOMContentLoaded", function () {
    const collapsibles = document.querySelectorAll(".collapsible");
    collapsibles.forEach(button => {
        button.addEventListener("click", function () {
            this.classList.toggle("active");
            const content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    });
});


let ingredientIndex = 1;
function addNewIngredient() {
    const container = document.getElementById("ingredients");
    const div = document.createElement("div");
    div.innerHTML = `
        <input type="text" name="ingredients[${ingredientIndex}].name" placeholder="Ingredient name" required>
        <input type="text" name="ingredients[${ingredientIndex}].measurement" placeholder="e.g. 2 cups">
        `;
        container.appendChild(div);
        ingredientIndex++;
}