<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <p>dsadsa</p>
  <!--HelloWorld msg="Welcome to Your Vue.js App"/-->
  <HelloJunum/>
  <HelloJunum/>
 
  {{msg}}
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import HelloJunum from './components/HelloJunum.vue'

export default {
  name: 'App',
  components: {
    HelloWorld,
    HelloJunum
  }, data() {
      msg : "heellooo"
  }
}
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
</style>
