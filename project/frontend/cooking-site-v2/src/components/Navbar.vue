<template>
 <nav>
    <v-app-bar flat app>
        <v-app-bar-nav-icon class="grey--text" @click="drawer = !drawer"></v-app-bar-nav-icon>
        <v-toolbar-title class="text-uppercase grey--text" >
            <span class="font-weight-light"> Cooking</span>
            <span class="font-weight-bold">Page</span>
        </v-toolbar-title>
    

    <v-spacer></v-spacer>

        <v-switch class = "mr-3"
            v-model="switchDB"
            :label= dbType
            @click="changeDB"
        ></v-switch>
        {{$root.currentUser.userName}}
        <v-btn depressed class="grey white--text mx-2"   to="/login">
            <span>Login</span>
            <v-icon right>login</v-icon>
        </v-btn>
        <v-btn depressed class="grey white--text mx-2" to="/register">
            <span>Register</span>
            <v-icon right>login</v-icon>
        </v-btn>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app :style="{background: $vuetify.theme.themes.light.primary}" md="4">
        <v-list>
            <v-list-item v-for="link in links" :key="link.text" router-link :to="link.route">
                <v-list-item-action>
                    <v-icon class="white--text"> {{ link.icon }} </v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title class="white--text">{{ link.text }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    
    </v-navigation-drawer>
 </nav>
</template>

<script>
export default {
    data() { 
        return {
            drawer: true,
            links: [
                {icon: 'home', text: 'Main Page', route: '/'},
                {icon: 'restaurant', text: 'All Recipes', route: '/recipeView'},
                {icon: 'trending_up', text: 'Top 10 Recipes', route: '/ratingView'},
                {icon: 'post_add', text: 'Write a new Recipe', route: '/newRecipe'},
                {icon: 'person', text: 'Account', route: '/account'},  
                {icon: 'people', text: 'Team: About Us', route: '/about'}
            ],
            switchDB: false,
            dbType: "SQL"
        }
    },
    methods:{
        changeDB(){
            if(this.switchDB) {
                this.$root.dbType = "NoSQL" 
                this.dbType = "NoSQL"
                console.log(this.switchDB,this.$root.dbType)
            }else{
                this.$root.dbType = "SQL"
                this.dbType = "SQL"
                console.log(this.switchDB,this.$root.dbType)
            }
            this.$router.push('Login')
            this.$root.currentUser.id = -1
            this.$root.currentUser.userName = "Guest"
        }
    }
}
</script>

<style>

</style>