<template>
    <div v-if="recipes.length">
        <div>
            ALL RECIPES
        </div>
        <div v-for="recipe in recipes" :key="recipe"> 
        <p>{{recipe.recipeName}} , </p>   
        <p>{{recipe.course}}, </p>   
        <p>{{recipe.cuisine}}</p>   
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            recipes: []
        }
    },
    methods: {
    },
    mounted(){
        fetch("http://localhost:8080/recipe/listRecipes")
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
    }
}
</script>

<style>

</style>