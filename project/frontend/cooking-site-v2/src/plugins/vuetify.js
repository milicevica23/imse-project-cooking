import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);
import colors from 'vuetify/lib/util/colors'

export default new Vuetify({
    theme: {
        themes: {
          light: {
            primary: colors.purple,
            secondary: colors.grey.darken1,
            accent: colors.shades.black,
            error: colors.red.accent3,
            background: colors.indigo.lighten5, // Not automatically applied
            primary: '#9652ff',
            success: '#3cd1c2',
            info: '#7ffaa2c',
            error: '#f83e70'
          },
          dark: {
            primary: colors.blue.lighten3, 
            background: colors.indigo.base, // If not using lighten/darken, use base to return hex
          } 
        },
      },
});
