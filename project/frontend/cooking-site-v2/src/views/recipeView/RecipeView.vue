<template>
    <v-container>
        <v-row>
            <v-col v-if="advaceFilteringInd" cols=3>
                <v-btn @click="changeAdvanceFiltering"> advance filter </v-btn>
                FILTER
            </v-col>
            <v-col v-else cols=3>
                <v-btn @click="changeAdvanceFiltering"> advance filter
                </v-btn>
                list lands
            </v-col>
            <v-col>
                <v-row>
                    <v-text-field
                    v-model="searchedRecipe"
                    :counter="20"
                    label=""
                    ></v-text-field>
                    <v-btn @click="search">
                        search
                    </v-btn>
                </v-row>
                <v-row v-for="recipe in recipes" :key="recipe.id">
                    {{recipe}}
                </v-row>
            </v-col>
            <v-col cols=1>
                HIGLIHT
            </v-col>
        </v-row>
    </v-container>


</template>

<script>
export default {
    data() {
        return {
            advaceFilteringInd: false,
            searchedRecipe: "",
            recipes: []
        }
    },
    methods: {
        changeAdvanceFiltering(){
            console.log("HEELLO")
            this.advaceFilteringInd = !this.advaceFilteringInd
        },
        search(){
            fetch("http://localhost:8080/filter/listRecipesSelected?recipename=" + this.searchedRecipe)
           .then(this.searchedRecipe.toLowerCase())
            .then(res => res.json())
            //.then(data => this.recipes = data)
            .then(data => this.recipes = data)
            .catch(e => console.log(e))
        }
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