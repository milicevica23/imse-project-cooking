<template>
  <v-app>
    <v-app-bar app color="indigo" dark>
      <div class = "Home">
        <h1>Hello</h1>  
      </div>
    </v-app-bar>



    <v-main>
      <router-link to="/recipeView"> Recipes</router-link> |
      <router-link to="/ratingView"> Ratings </router-link>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App'
 // , components: {}
};
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
body{
  margin: 0;
  background: #eee;
}
</style>