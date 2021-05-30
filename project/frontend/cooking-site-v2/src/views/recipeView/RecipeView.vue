<template>
    <div class="all">
        <h1 class="subheading grey--text"> All Recipes</h1>

        <v-container>
            <v-row>
                <v-col md="12">
                    <v-row>
                        <v-text-field
                            v-model="searchedRecipe"
                            :counter="20"
                            label=""
                        ></v-text-field>
                            <v-btn depressed class="pink white--text" mx-2 @click="search">
                                <span>search</span>
                                <v-icon right>search</v-icon>
                            </v-btn>
                            <v-btn fab small dark class="red mx-2">
                                <v-icon>favorite</v-icon>
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
            advaceFilteringInd: false,
            searchedRecipe: "",
            recipes: '',
            context: "listRecipe"
        }
    },
    methods: {
        search(){
            fetch(this.$root.baseUrl + "/recipe/getRecipes?recipeName=" +this.searchedRecipe + "&dbType=" + this.$root.dbType )
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
        }
    },
    mounted(){
        fetch(this.$root.baseUrl + "/recipe/getRecipes?recipeName=" +this.searchedRecipe + "&dbType=" + this.$root.dbType )
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
    }
}
</script>

<style>

</style>