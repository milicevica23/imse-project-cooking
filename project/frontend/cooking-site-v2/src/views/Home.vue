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
              <v-btn class="ml-2" @click="dropSQL"> 
                drop SQL data
              </v-btn>
           </v-row>
            <v-row v-for="(key,value) in configs" :key="value">
               {{value}}: {{key}}
            </v-row>
            
         </v-col>
         <v-col cols=6>
           <v-row>
              <v-btn @click="migrateDB"> 
              migrate Data from SQL to NoSQL
              </v-btn>
              <v-btn class="ml-2" @click="dropNoSql"> 
                drop NoSQL data 
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
        fetch(this.$root.baseUrl + "/configuration/createDB")
        .catch(e => console.log(e))
      },
      migrateDB() {
        fetch(this.$root.baseUrl + "/configuration/migrateData")
        .catch(e => console.log(e))
      },
      dropSQL() {
        fetch(this.$root.baseUrl + "/configuration/dropData?dbType=SQL")
        .catch(e => console.log(e))
        this.$root.currentUser.id = -1
        this.$root.currentUser.userName = "Guest"
      },
      dropNoSql() {
        fetch(this.$root.baseUrl + "/configuration/dropData?dbType=NoSQL")
        .catch(e => console.log(e))
        this.$root.currentUser.id = -1
        this.$root.currentUser.userName = "Guest"
      }
    },
    mounted(){
        fetch(this.$root.baseUrl + "/configuration/getConfiguration",{
          headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Headers" : "Content-Type",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "OPTIONS,POST,GET"
        
              }
        })
        .then(res => res.json())
        .then(data => this.configs = data)
        .catch(e => console.log(e))
    }
  }
</script>
