<template>
    <div class="selected">
        <h1 class="subheading grey--text"> {{recipe.recipe_name}}}}</h1>

        <v-container>
            <v-row>
                <v-col md="6" sm="12">
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
            recipes: []
        }
    },
    methods: {
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