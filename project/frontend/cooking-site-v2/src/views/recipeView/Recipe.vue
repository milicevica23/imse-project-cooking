<template>
<v-container>
  <v-card>
    <v-row align="center" justify="center">

    <v-list-item two-line>
      <v-list-item-content>
        <v-list-item-title class="text-h5" align="right" justify="right">
          {{recipe.username}}
        </v-list-item-title>
        <v-list-item-subtitle align="right" justify="right">{{recipe.date}}</v-list-item-subtitle>
         <v-list-item-title >
            <span> Rating: {{recipe.avg_rating.toFixed(1)}} </span>
            <v-icon left class="rating-star" style="color:orange">grade</v-icon>
        </v-list-item-title>

            
      </v-list-item-content>
    </v-list-item>

    <v-col cols=6 v-if="context === 'oneRecipe'" align="center" justify="center">
        <v-rating
            v-model="recipe.avg_rating"
            background-color="yellow lighten-3"
            color="yellow"
        ></v-rating>
        <v-btn @click="addRating"> send rating</v-btn>
        <p v-if="rating_message !==''"> {{rating_message}}</p>
    </v-col>
    
    </v-row>
    <v-row v-if="context === 'creating'">
        <v-col>
            <v-card-title class="justify-center">{{recipe.recipe_name}}</v-card-title>
        </v-col>
    </v-row>

    <v-row>
        <v-col>
            <v-img class="white--text align-end" height="300px" :src="recipe.cover_photo.link">
            </v-img>
            <!--v-img contain :height=photoHeight :src="recipe.cover_photo.link"></v-img-->
        </v-col>
    </v-row>

    <v-row v-if="context === 'listRecipe'">
        <v-col>
            <v-card-title primary-title class="justify-center">
                <div>
                    <h3 class="headline grey--text text--accent-2 font-weight-bold"> {{recipe.recipe_name}} </h3>
                </div>
            </v-card-title>
        </v-col>
    </v-row>

    <v-card-text class="text--primary"  align="center" justify="center">
        <div>Preparation Time: {{recipe.preparation_time}}</div>

        <div>Cooking Time: {{recipe.cooking_time}} </div>

        <div>Course: {{recipe.course}} </div>

        <div>Cuisine: {{recipe.cuisine}} </div>
    </v-card-text>
    
    <v-row justify="center" align="center">
        
            <v-chip-group active-class="deep-purple accent-4 white--text"  align="center" justify="center">
                <v-chip v-for="(item, index) in recipe.ingredient" :key="index" >
                    {{item.ingredient_name}}: {{item.amount}}
                </v-chip>
            </v-chip-group>
      
    </v-row>
        
    <v-row v-for="(step,index) in recipe.instructions" :key="index" >
        <v-col class="pl-16"  justify="center" >
            <span class=" orange--text text--accent-2 font-weight-bold">Step {{step.step_num}}: </span>
            <span>{{step.content}} </span>
        </v-col>
    </v-row>
    <v-row v-if="context === 'listRecipe'">
        <v-col align="center" justify="center">
            <v-btn class="blue white--text ma-3 lighten-2" @click="openSelectedRecipe">
                View Recipe
            </v-btn>
        </v-col>
    </v-row>
  </v-card>

    <v-col></v-col>
    <v-col></v-col>

  <v-card v-for="(comment,index) in recipe.comments" :key="index" color="grey lighten-1">
      <v-row class="ma-4">
       <v-list-item two-line>
            <v-list-item-content>
                <v-list-item-subtitle class="purple--text"> {{comment.username}} </v-list-item-subtitle>
                <v-list-item-title >{{comment.date}}:  {{comment.content}}</v-list-item-title> 
            </v-list-item-content>
        </v-list-item>
      </v-row>
  </v-card>

  <v-form v-if="context === 'oneRecipe'">
          <v-container>
            <v-text-field
                v-model="new_comment"
                
                placeholder="Leave a comment..."
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
        photoHeight: 400,
        rating_message : ""
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
              headers: {
              'Content-Type': 'application/json',
              },
              method: "POST",
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
            if(this.$root.currentUser.userName!=="Guest") {
                this.$router.push({name: 'Select', params: { recipe_id: this.recipe._id}})
            } else {
                this.$router.push('Login') 
            }
            
        },
        addRating(){
            var current = new Date();
                            
            var payload = {
                recipe_id: this.recipe._id,
                user_id: this.$root.currentUser.id,
                date: current.toISOString().split('T')[0],
                rating: this.recipe.avg_rating
            };
        
            var self = this;

            fetch(this.$root.baseUrl + "/recipe/addRating/?dbType=" + this.$root.dbType,
            {
                headers: {
                 'Content-Type': 'application/json',
                },
                method: "POST",
                body: JSON.stringify(payload)
            })
            .then((res) => { return res.json(); })
            .then((data) => { 
                    if(data.status === "ok"){
                        this.recipe.avg_rating = data.new_avg_rating
                    }else{
                        this.rating_message = data.status
                        this.recipe.avg_rating = data.new_avg_rating
                    }

                    console.log(data.status)
            }) 
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