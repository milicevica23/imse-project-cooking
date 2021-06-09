<template>
<v-container>
  <v-card>
    <v-row align="center" justify="center">
        <v-col class="ml-4" cols=2>{{recipe.date}}</v-col>
        <v-col class="ml-1" cols=2>{{recipe.username}}</v-col>
        <v-spacer></v-spacer>
        <v-col cols=6 v-if="context === 'oneRecipe'" align="center" justify="center">
            <v-rating
                v-model="recipe.avg_rating"
                background-color="purple lighten-3"
                color="purple"
            ></v-rating>
        </v-col>
        <v-col cols=6 class="mr-4" v-if="context === 'listRecipe'" align="right" justify="center">
            Rating: {{recipe.avg_rating}}
        </v-col>
    </v-row>
    <v-row v-if="context === 'creating'">
        <v-col>
            <v-card-title class="justify-center">{{recipe.recipe_name}}</v-card-title>
        </v-col>
    </v-row>
    <v-row>
        <v-col>
            <v-img
                contain
                :height=photoHeight
                :src="recipe.cover_photo.link"
            ></v-img>
        </v-col>
    </v-row>
    <v-row v-if="context === 'listRecipe'">
        <v-col>
            <v-card-title class="justify-center">{{recipe.recipe_name}}</v-card-title>
        </v-col>
    </v-row>
    <v-row>
        <v-col align="center" justify="center">
            {{recipe.preparation_time}}
        </v-col>
        <v-col align="center" justify="center">
             {{recipe.cooking_time}}
        </v-col>
        <v-col align="center" justify="center">
             {{recipe.course}}
        </v-col>
        <v-col align="center" justify="center">
             {{recipe.cuisine}}
        </v-col>
    </v-row >
    <v-row justify="center"
            align="center">
        <v-col class="ml-5">
            <v-chip-group
            active-class="deep-purple accent-4 white--text"
            column
        >
            <v-chip v-for="(item, index) in recipe.ingredient" :key="index" >
                {{item.ingredient_name}}: {{item.amount}}
            </v-chip>
            </v-chip-group>
        </v-col>
    </v-row>
        

    <v-row v-for="(step,index) in recipe.instructions" :key="index" >
        <v-col class="pl-16" align="center" justify="center" >
            {{step.step_num}}: {{step.content}}
        </v-col>
    </v-row>
    <v-row v-if="context === 'listRecipe'">
        <v-col align="center" justify="center">
            <v-btn class="blue white--text ma-3" @click="openSelectedRecipe">
                View Recipe
            </v-btn>
        </v-col>
    </v-row>
  </v-card>
  <v-card v-for="(comment,index) in recipe.comments" :key="index" >
      <v-row class="ma-4">
          <v-col>
            {{comment.date}} : {{comment.username}} : {{comment.content}}  
          </v-col>
      </v-row>
  </v-card>
  <v-form v-if="context === 'oneRecipe'">
          <v-container>
            <v-text-field
                v-model="new_comment"
                label="comment"
                required
                :rules="[v => !!v || 'field is required']"
            ></v-text-field>
            <v-btn
                class="mr-4"
                @click="submitComment"
            >
                comment
            </v-btn>
                <v-btn @click="clear">
                clear
            </v-btn>
          </v-container>
    </v-form>
</v-container>
</template>

<script>
export default {
    props: ['recipe', 'context'],
    data: () => ({
        new_comment:"",
        photoHeight: 400  
    }),
    methods:{
        submitComment(){
            var current = new Date();

            this.recipe.comments.push({
                username : this.$root.currentUser.userName,
                date : current.toISOString().split('T')[0],
                content : this.new_comment})
                
            
            var payload = {
                recipe_id: this.recipe._id,
                user_id: this.$root.currentUser.id,
                date: current.toISOString().split('T')[0],
                content: this.new_comment
            };
        
          var self = this;

          fetch(this.$root.baseUrl + "/recipe/addComment/?dbType=" + this.$root.dbType,
          {
              method: "POST",
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(payload)
          })
          .then((res) => { return res.json(); })
          .then((data) => { 
                console.log(data.status)
           }) 

            this.new_comment = ' '
        },
        clear(){

        },
        openSelectedRecipe(){
            console.log("recipe: " + this.recipe._id)
            this.$router.push({name: 'Select', params: { recipe_id: this.recipe._id}})
        }
    },
    mounted(){
        if(this.context==="listRecipe"){
            this.photoHeight=200
        }
    }


}
</script>

<style>

</style>