<template>
  <div>
      <h1 class="subheading grey--text">
        Create a CookingPage.com Account
      </h1>
      <v-form v-model="valid">
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
                hint="At least 8 characters"
                :rules="[
                    v => !!v || 'field is required',
                    v => (v && v.length >= 8) || 'Password must have at least 8 characters'
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
                hint="At least 8 characters"
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
          </v-container>
        </v-form>
  </div>
</template>

<script>



  export default {

    data: () => ({
      name: '',
      email: '',
      username: '',
      password: '',
      confirm_password: '',
      select: null,
      checkbox: false,
      show:false

    }),
    methods: {
      submit () {
      },
      clear () {

        this.name = ''
        this.email = ''
        this.username = ''
        this.password = ''
        this.confirm_password = ''
        this.checkbox = false
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