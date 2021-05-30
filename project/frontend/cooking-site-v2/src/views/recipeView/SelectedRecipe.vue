<template>
    <v-row v-if="recipe !==''">
        <v-col>
            <Recipe :recipe=recipe :context="context"/>
        </v-col>
    </v-row>
</template>


<script>
import Recipe from '@/views/recipeView/Recipe.vue'
export default {
    
    components: {
      Recipe
    },
    
    data() {
        return {
            recipe: '',
            context:"oneRecipe"
        }
    },
    methods: {
    },
    mounted(){
       console.log(this.$route.params.recipe_id) 
       fetch(this.$root.baseUrl + "/recipe/getOneRecipe?recipeId=" + this.$route.params.recipe_id + "&dbType=" + this.$root.dbType )
            .then(res => res.json())
            .then(data => this.recipe = data)
            .catch(e => console.log(e))
    }
}
</script>

<style>

</style>