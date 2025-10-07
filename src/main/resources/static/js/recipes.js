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

document.addEventListener("DOMContentLoaded", () => {
    // find add button and ingredient container (supports both new- and edit-pages)
    const addBtn = document.querySelector(".add-ingredient");
    let ingredientsContainer = document.getElementById("ingredients-container");

    // fallback: if no dedicated container, fall back to the .ingredients div
    if (!ingredientsContainer) {
        const ingredientsDiv = document.querySelector(".ingredients");
        if (ingredientsDiv) {
        // If the .ingredients div contains ingredient rows directly, use it.
        // If it contains a #ingredients-container inside (rare), that will be used instead.
            ingredientsContainer = ingredientsDiv.querySelector("#ingredients-container") || ingredientsDiv;
        }
    }


    if (!ingredientsContainer) {
        // nothing to do on pages without an ingredients area
        console.info("No ingredients container found; recipes.js add/remove will be inactive.");
        return;

    }

    // Helper: re-index all ingredient rows so names are ingredients[i].name / ingredients[i].measurement
    function reindexIngredientRows() {
        const rows = ingredientsContainer.querySelectorAll(".ingredient-row");
        rows.forEach((row, idx) => {
        const nameInput = row.querySelector('input[data-role="ing-name"]') || row.querySelector('input[name$=".name"]') || row.querySelector('input[type="text"]');
        const measureInput = row.querySelector('input[data-role="ing-measure"]') || row.querySelectorAll('input')[1] || row.querySelector('input[type="text"]:nth-of-type(2)');

        if (nameInput) {
            nameInput.name = `ingredients[${idx}].name`;
            nameInput.id = `ingredients_${idx}_name`;
        }
        if (measureInput) {
            measureInput.name = `ingredients[${idx}].measurement`;
            measureInput.id = `ingredients_${idx}_measurement`;
        }
    });

}

    // ensure any existing remove buttons won't submit the form accidentally
    function ensureRemoveButtonsAreSafe() {
        ingredientsContainer.querySelectorAll(".remove-ingredient").forEach(btn => {
            if (!btn.hasAttribute("type")) btn.setAttribute("type", "button");
    });
}

// initial normalization (make sure server-rendered rows also have consistent names)
reindexIngredientRows();
ensureRemoveButtonsAreSafe();

// Event delegation: handle remove clicks for any .remove-ingredient inside the container
ingredientsContainer.addEventListener("click", (e) => {
    const removeBtn = e.target.closest(".remove-ingredient");
    if (!removeBtn) return;
        e.preventDefault();
        const row = removeBtn.closest(".ingredient-row");
    if (row) {
        row.remove();
        reindexIngredientRows();
    }
});

// Add new ingredient row when add button clicked
if (addBtn) {
    addBtn.addEventListener("click", (e) => {
    e.preventDefault();

    // create a new row
    const row = document.createElement("div");
    row.className = "ingredient-row d-flex gap-2 align-items-center mb-2";

    row.innerHTML = `
        <input class="form-control" data-role="ing-name" placeholder="Ingredient name" required/>
        <input class="form-control" data-role="ing-measure" placeholder="Measurement (optional)" />
        <button type="button" class="btn btn-outline-danger btn-sm remove-ingredient" title="Remove ingredient">
        <i class="bi bi-trash"></i>
        </button>
    `;

    // Insert row *before* the add button if add button is inside the same parent,
    // otherwise append to container.
    if (addBtn.parentElement === ingredientsContainer) {
        ingredientsContainer.insertBefore(row, addBtn);
    } else {
        // sometimes the add button is outside the actual rows container
        // in that case append to the container
        ingredientsContainer.appendChild(row);
    }

    reindexIngredientRows();
    ensureRemoveButtonsAreSafe();
    // focus the new name input
    const newNameInput = row.querySelector('[data-role="ing-name"]');
    if (newNameInput) newNameInput.focus();
});

}

// if user presses Enter in an ingredient name/measure input, do not submit the whole form
ingredientsContainer.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
        const insideInput = e.target.matches('input');
        if (insideInput) {
                e.preventDefault(); // keep focus/allow multi-line only with textarea if needed
                return;
        }
    }
    });
});
