<template>
  <v-app :style="{background: $vuetify.theme.themes.light.background}">
    <Navbar/>

    <v-main class="mx-4 mb-4">
      <router-view/>
    </v-main>

  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'App',
  components: {Navbar}
};
</script>