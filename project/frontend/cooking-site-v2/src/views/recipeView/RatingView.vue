<template>
    <div class="rating">
        <h1 class="subheading grey--text"> Best Rated Recipes</h1>
        <v-container class="my-5">
            <v-row>
                <v-col md="12">
                    <v-row>
                        <v-text-field
                        v-model="searchedRecipe" :counter="20" label=""></v-text-field>
                        <v-btn depressed class="pink white--text" @click="search">
                            <span>search</span>
                            <v-icon right>search</v-icon>
                        </v-btn>
                    </v-row>
                </v-col>
                <v-row v-if="recipes !==''">
                    <v-col v-for="recipe in recipes" :key="recipe.id" md="6" sm="12">
                        <Recipe :recipe=recipe :context="context" />
                    </v-col>
                </v-row>
                
            </v-row>
        </v-container>

    </div>
</template>


<script>
import Recipe from '@/views/recipeView/Recipe.vue'
export default {
    components: {
      Recipe
    },
    data() {
        return {
            recepiname: "",
            recipes: "",
            searchedRecipe: "",
            context: "listRecipe"
        }
    },
     methods: {
        search(){
            fetch(this.$root.baseUrl + "/recipe/getRecipes?recipeName=" +this.searchedRecipe + "&dbType=" + this.$root.dbType +"&filterOrder=desc")
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
        }
    },
    mounted(){
        fetch(this.$root.baseUrl + "/recipe/getRecipes?recipeName=" +this.searchedRecipe + "&dbType=" + this.$root.dbType + "&filterOrder=desc")
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
    }
}
</script>

<style>

</style>