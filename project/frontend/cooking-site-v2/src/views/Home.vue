<template>
  <div class="home"> 
    <h1 class="subheading grey--text">Homepage</h1>
    <v-container class="my-5">
       <v-row>
         <v-col cols=6>
           <v-row>
              <v-btn @click="createDB"> 
                create SQL and generate Data 
              </v-btn>
           </v-row>
            <v-row>
              {{configs}}
            </v-row>
            
         </v-col>
         <v-col cols=6>
           <v-row>
              <v-btn @click="migrateDB"> 
              migrate Data from SQL to NoSQL
              </v-btn>
           </v-row>
           
    
         </v-col>
       </v-row>
    </v-container>
  </div>
  
</template>

<script>

  export default {
    name: 'Home',

    components: {
    },
    data: () => ({
      configs: []
    }),
    methods: {
      createDB () {
        fetch("http://localhost:8080/configuration/createDB")
        .catch(e => console.log(e))
      },
      migrateDB() {
        fetch("http://localhost:8080/configuration/migrateData")
        .catch(e => console.log(e))
      }
    },
    mounted(){
        fetch("http://localhost:8080/configuration/getConfiguration")
        .then(res => res.json())
        .then(data => this.configs = data)
        .catch(e => console.log(e))
    }
  }
</script>
