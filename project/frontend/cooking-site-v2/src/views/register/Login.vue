<template>
  <div>
      <h1 class="subheading grey--text">
        Login to CookingPage.com Account
      </h1>
      <v-form>
          <v-container>
            <v-text-field
                v-model="username"
                label="Username"
                required
                :rules="[v => !!v || 'field is required']"
            ></v-text-field>

            <v-text-field
                v-model="password"
                label="Password"
                :type="show ?'text': 
                'password'"
                :append-icon="show ?'mdi-eye':'mdi-eye-off'"   
                @click:append="show=!show"
                required
                hint="At least 4 characters"
                :rules="[
                    v => !!v || 'field is required',
                    v => (v && v.length >= 4) || 'Password must have at least 8 characters'
                ]"
            ></v-text-field>


            <v-btn
                class="mr-4"
                @click="submit"
            >
                login
            </v-btn>
                <v-btn @click="clear">
                clear
            </v-btn>
            <p class="ma-3" v-if="message !== ''">{{message}}</p>
          </v-container>
        </v-form>
  </div>
</template>

<script>



  export default {

    data: () => ({
      username: '',
      password: '',
      show:false,
      message : ''
      
    }),
    methods: {
      submit () {
        var self = this;
        fetch(this.$root.baseUrl + "/user/login/?userName=" + this.username + "&userPassword="+ this.password + "&dbType=" + this.$root.dbType)
            .then(res => res.json())
            .then((data) => {
              if(data.status === "userExists"){
                self.$root.currentUser.id = data._id;
                self.$root.currentUser.userName = data.username;
                this.message = "Loged as " + data.username
              }else{
                this.message = "No user found"
              }
            }).catch(e => console.log(e))
      },
      clear () {
        this.username = ''
        this.password = ''
        this.message = ''
      }
    }
  }
</script>

<style>
  form {
    max-width: 700px;
    margin: 30px auto;
    background: white;
    text-align: left;
    padding: 40px;
    border-radius: 10px;
  }

</style>