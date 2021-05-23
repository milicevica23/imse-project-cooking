<template>
    <div class="all">
        <h1 class="subheading grey--text"> All Recipes</h1>

        <v-container>
            <v-row>
                <v-col md="12">
                    <v-row>
                        <v-btn col="3" @click="changeAdvanceFiltering"> advance filter </v-btn>
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

                <v-col v-for="recipe in recipes" :key="recipe.id" md="6" sm="12">
                    <v-card class="pa-12">
                        <v-img
                            height="250"
                            :src="recipe.link"
                            ></v-img>
                        <v-row  class="pa-8"> 
                           {{recipe.recipe_name}}
                        </v-row>
                        <v-row>
                            <v-col>
                               {{recipe.username}}
                            </v-col>
                            <v-col>
                                <v-row>
                                    Prep Time: {{recipe.preparation_time}}
                                </v-row>
                                <v-row>
                                    Cooking Time: {{recipe.cooking_time}}
                                </v-row>
                                <v-row :style="{left: '50%', transform:'translateX(-50%)'}">
                                    <v-btn class="blue white--text ma-3" to="/selectedRecipe" >
                                        View Recipe
                                    </v-btn>
                                </v-row>
                                
                            </v-col>
                        </v-row>
               
                    </v-card>
                </v-col>
                

            </v-row>
        </v-container>
    </div>
</template>


<script>
export default {
    data() {
        return {
            advaceFilteringInd: false,
            searchedRecipe: "",
            recipes: [{route: '/selectedRecipe'}]
        }
    },
    methods: {
        changeAdvanceFiltering(){
            console.log("HEELLO")
            this.advaceFilteringInd = !this.advaceFilteringInd
        },
        search(){
            fetch("http://localhost:8080/filter/listRecipesSelected?recipename=" + this.searchedRecipe)
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
        }
    },
    mounted(){
        fetch("http://localhost:8080/filter/listCoverRecipes")
            .then(res => res.json())
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
    }
}
</script>

<style>

</style>