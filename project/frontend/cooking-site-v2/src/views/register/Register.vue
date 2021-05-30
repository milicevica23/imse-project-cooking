<template>
  <div>
      <h1 class="subheading grey--text">
        Create a CookingPage.com Account
      </h1>
      <v-form >
          <v-container>
            <v-text-field v-model="name"
                :counter="10"
                label="Name"
                required
                :rules="[v => !!v || 'field is required']"
            ></v-text-field>

            <v-text-field
                v-model="email"
                label="E-mail"
                required
                :rules="[v => !!v || 'field is required']"
            ></v-text-field>

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

            <v-text-field
                v-model="confirm_password"
                label="Repeat Password"
                :type="show ?'text': 
                'password'"
                :append-icon="show ?'mdi-eye':'mdi-eye-off'"   
                @click:append="show=!show"
                required
                hint="At least 4 characters"
                :rules="[(password === confirm_password) || 'Passwords must match']"
            ></v-text-field>

            <v-checkbox
                v-model="checkbox"
                :rules="[v => !!v || 'You must agree to continue!']"
                label="I agree to the terms and conditions"
                required
            ></v-checkbox>

            <v-btn
                class="mr-4"
                @click="submit"
            >
                submit
            </v-btn>
                <v-btn @click="clear">
                clear
            </v-btn>
            <p v-if="message !== ''"> {{message}}</p>
          </v-container>
        </v-form>
  </div>
</template>

<script>



  export default {

    data: () => ({
      name: 'a',
      email: 'a',
      username: 'a',
      password: '1234',
      confirm_password: '1234',
      select: null,
      checkbox: true,
      show:false,
      message: ''

    }),
    methods: {
      submit () {
        if(this.checkbox && this.password === this.confirm_password && this.name !== '' && this.email !== '' && this.username !== '' && this.password !== ''){
          var payload = {
            name: this.name,
            username: this.username,
            password: this.password,
            email: this.email
          };
        
          var self = this;

          fetch(this.$root.baseUrl + "/user/register/?dbType=" + this.$root.dbType,
          {
              method: "POST",
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(payload)
          })
          .then((res) => { return res.json(); })
          .then((data) => { 
                if(data.status === "ok"){
                  this.message = data.message
                }else{
                  this.message = data.status
                }
           }) 
        }else{
          // for everything add message
          this.message = "smt is wrong";
        }
      },
      clear () {
        this.name = ''
        this.email = ''
        this.username = ''
        this.password = ''
        this.confirm_password = ''
        this.checkbox = false
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