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
                <v-col v-for="recipe in recipes" :key="recipe.id"  md="6" sm="12">
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
                                   Rating: {{recipe.average_r}} 
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
            recepiname: "",
            recipes: [],
            searchedRecipe: ""
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
        fetch("http://localhost:8080/filter/listRecipesRatingDesc")
        .then(res => res.json())
        .then(data => this.recipes = data)
        .catch(e => console.log(e))
    }
}
</script>

<style>

</style>