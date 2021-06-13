<template>
  <div class="newRecipe">
    <h1 class="subheading grey--text">This is an  new recipe</h1>
    
    <v-container class="my-5">
       <v-row>
        <v-col cols=6>
          <v-form :v-if=false>
          <v-container>
            <v-text-field
                v-model="recipe.recipe_name"
                label="recipe name"
                required
            ></v-text-field>
            <v-text-field
                v-model="recipe.cover_photo.link"
                label="recipe cover photo"
                required
            ></v-text-field>
            <v-row>
              <v-col cols=6>
                <v-text-field cols=6
                  v-model="recipe.course"
                  label="course"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols=6>
                <v-text-field cols=6
                  v-model="recipe.cuisine"
                  label="cuisine"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols=6>
                <v-text-field cols=6
                  v-model="recipe.preparation_time"
                  label="preparation time"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols=6>
                <v-text-field cols=6
                  v-model="recipe.cooking_time"
                  label="cooking time"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols=6>
                <v-text-field cols=6
                  v-model="temp_ingredient"
                  label="ingredient"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols=6>
                <v-text-field cols=6
                  v-model="temp_ingredient_type"
                  label="amount"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row >
              <v-col>
                <v-btn
                class="mr-4"
                @click="addIngredient"
                >
                  add ingredient
                </v-btn>
                <v-btn @click="clearIngredient">
                    clear Ingredient
                </v-btn>
              </v-col>
              
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                v-model="step_content"
                label="step_content"
              ></v-text-field>
              </v-col>
              
            </v-row>
            
            <v-row >
              <v-col>
                <v-btn
                  class="mr-4"
                  @click="addStep"
                >
                  add new step
                </v-btn>
              
                <v-btn @click="clearStep">
                    clear step
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
        <v-row class="ma-2">
          <v-col align="center" justify="center">
              <v-btn
                  class="mr-4"
                  @click="submit"
              >
              add recipe
            </v-btn>
          </v-col>
         
        </v-row>
        </v-col>
        <v-col cols=6>
          <Recipe :recipe=recipe :context=context />
        </v-col>
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

    data: () => ({
      context:'creating',
      temp_ingredient: "",
      temp_ingredient_type: "",
      step_content: "",
      step_num: 0,
      recipe : {
        recipe_name: 'burger',
        date: '',
        preparation_time: '30',
        cooking_time: '20',
        course: 'breakfast',
        cuisine: 'serbien',
        username: 'aleks',
        avg_rating: 4.5,
        cover_photo: {
            link: "https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg"
        },
        ingredient: [
          {
            ingredient_name : "salat",
            amount: "3"
          },
          {
            ingredient_name : "cheese",
            amount: "3 slices"
          },
          {
            ingredient_name : "meat",
            amount: "3"
          },
        ],
        comments: [
        ],
        instructions: [
            {
            step_num: 1,
            content: "prepare onions"
            },
            {
                step_num: 2,
                content: "Du bist eine legende"
            }
        ]
    }

    }),
    methods: {
      addIngredient(){
        if(this.temp_ingredient !== "" && this.temp_ingredient_type !== ""){
          this.recipe.ingredient.push({
              ingredient_name: this.temp_ingredient,
              amount: this.temp_ingredient_type
          })
          this.temp_ingredient = ""
          this.temp_ingredient_type = ""
        }
      },
      clearIngredient(){
        this.recipe.ingredient.pop()
      },
      addStep(){
        if (this.step_content !== ""){
          this.recipe.instructions.push({
              step_num: ++this.step_num,
              content: this.step_content
          })
        }
        this.step_content = ""
        
      },
      clearStep(){
        this.recipe.instructions.pop()
      },
      submit () {
        var payload = this.recipe
        payload.user_id = this.$root.currentUser.id
        
        var self = this;

        fetch(this.$root.baseUrl + "/recipe/insertNewRecipe/?dbType=" + this.$root.dbType,
        {
            method: "POST",
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload)
        })
        .then((res) => { return res.json(); })
        .then((data) => { 
            if( data.status ==="ok"){
              console.log(data._id);
              this.$router.push({name: 'Select', params: { recipe_id: data._id}})
            }else{
              console.log(data.status)
            }
              
        })
      },
      clear () {
        
      }
    },
    mounted(){
        if(this.$root.currentUser.userName === "Guest"){
          this.$router.push('Login') 
        }
        var current = new Date();
        this.recipe.date = current.toISOString().split('T')[0]

        this.recipe.username = this.$root.currentUser.userName

    }
  }
</script>
