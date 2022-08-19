import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'security-user',
        data: { pageTitle: 'vksApplicationApp.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'vksApplicationApp.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'vksApplicationApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      {
        path: 'society',
        data: { pageTitle: 'vksApplicationApp.society.home.title' },
        loadChildren: () => import('./society/society.module').then(m => m.SocietyModule),
      },
      {
        path: 'state',
        data: { pageTitle: 'vksApplicationApp.state.home.title' },
        loadChildren: () => import('./state/state.module').then(m => m.StateModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'vksApplicationApp.district.home.title' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'taluka',
        data: { pageTitle: 'vksApplicationApp.taluka.home.title' },
        loadChildren: () => import('./taluka/taluka.module').then(m => m.TalukaModule),
      },
      {
        path: 'village',
        data: { pageTitle: 'vksApplicationApp.village.home.title' },
        loadChildren: () => import('./village/village.module').then(m => m.VillageModule),
      },
      {
        path: 'parameter-lookup',
        data: { pageTitle: 'vksApplicationApp.parameterLookup.home.title' },
        loadChildren: () => import('./parameter-lookup/parameter-lookup.module').then(m => m.ParameterLookupModule),
      },
      {
        path: 'society-assets',
        data: { pageTitle: 'vksApplicationApp.societyAssets.home.title' },
        loadChildren: () => import('./society-assets/society-assets.module').then(m => m.SocietyAssetsModule),
      },
      {
        path: 'society-banks-details',
        data: { pageTitle: 'vksApplicationApp.societyBanksDetails.home.title' },
        loadChildren: () => import('./society-banks-details/society-banks-details.module').then(m => m.SocietyBanksDetailsModule),
      },
      {
        path: 'society-assets-data',
        data: { pageTitle: 'vksApplicationApp.societyAssetsData.home.title' },
        loadChildren: () => import('./society-assets-data/society-assets-data.module').then(m => m.SocietyAssetsDataModule),
      },
      {
        path: 'society-config',
        data: { pageTitle: 'vksApplicationApp.societyConfig.home.title' },
        loadChildren: () => import('./society-config/society-config.module').then(m => m.SocietyConfigModule),
      },
      {
        path: 'society-prerequisite',
        data: { pageTitle: 'vksApplicationApp.societyPrerequisite.home.title' },
        loadChildren: () => import('./society-prerequisite/society-prerequisite.module').then(m => m.SocietyPrerequisiteModule),
      },
      {
        path: 'ledger-accounts',
        data: { pageTitle: 'vksApplicationApp.ledgerAccounts.home.title' },
        loadChildren: () => import('./ledger-accounts/ledger-accounts.module').then(m => m.LedgerAccountsModule),
      },
      {
        path: 'account-mapping',
        data: { pageTitle: 'vksApplicationApp.accountMapping.home.title' },
        loadChildren: () => import('./account-mapping/account-mapping.module').then(m => m.AccountMappingModule),
      },
      {
        path: 'society-crop-registration',
        data: { pageTitle: 'vksApplicationApp.societyCropRegistration.home.title' },
        loadChildren: () =>
          import('./society-crop-registration/society-crop-registration.module').then(m => m.SocietyCropRegistrationModule),
      },
      {
        path: 'society-npa-setting',
        data: { pageTitle: 'vksApplicationApp.societyNpaSetting.home.title' },
        loadChildren: () => import('./society-npa-setting/society-npa-setting.module').then(m => m.SocietyNpaSettingModule),
      },
      {
        path: 'expenditure-type',
        data: { pageTitle: 'vksApplicationApp.expenditureType.home.title' },
        loadChildren: () => import('./expenditure-type/expenditure-type.module').then(m => m.ExpenditureTypeModule),
      },
      {
        path: 'schemes-details',
        data: { pageTitle: 'vksApplicationApp.schemesDetails.home.title' },
        loadChildren: () => import('./schemes-details/schemes-details.module').then(m => m.SchemesDetailsModule),
      },
      {
        path: 'society-loan-product',
        data: { pageTitle: 'vksApplicationApp.societyLoanProduct.home.title' },
        loadChildren: () => import('./society-loan-product/society-loan-product.module').then(m => m.SocietyLoanProductModule),
      },
      {
        path: 'gr-interest-details',
        data: { pageTitle: 'vksApplicationApp.gRInterestDetails.home.title' },
        loadChildren: () => import('./gr-interest-details/gr-interest-details.module').then(m => m.GRInterestDetailsModule),
      },
      {
        path: 'member',
        data: { pageTitle: 'vksApplicationApp.member.home.title' },
        loadChildren: () => import('./member/member.module').then(m => m.MemberModule),
      },
      {
        path: 'documents',
        data: { pageTitle: 'vksApplicationApp.documents.home.title' },
        loadChildren: () => import('./documents/documents.module').then(m => m.DocumentsModule),
      },
      {
        path: 'member-bank',
        data: { pageTitle: 'vksApplicationApp.memberBank.home.title' },
        loadChildren: () => import('./member-bank/member-bank.module').then(m => m.MemberBankModule),
      },
      {
        path: 'member-assets',
        data: { pageTitle: 'vksApplicationApp.memberAssets.home.title' },
        loadChildren: () => import('./member-assets/member-assets.module').then(m => m.MemberAssetsModule),
      },
      {
        path: 'member-land-assets',
        data: { pageTitle: 'vksApplicationApp.memberLandAssets.home.title' },
        loadChildren: () => import('./member-land-assets/member-land-assets.module').then(m => m.MemberLandAssetsModule),
      },
      {
        path: 'nominee',
        data: { pageTitle: 'vksApplicationApp.nominee.home.title' },
        loadChildren: () => import('./nominee/nominee.module').then(m => m.NomineeModule),
      },
      {
        path: 'bank-dhoran-details',
        data: { pageTitle: 'vksApplicationApp.bankDhoranDetails.home.title' },
        loadChildren: () => import('./bank-dhoran-details/bank-dhoran-details.module').then(m => m.BankDhoranDetailsModule),
      },
      {
        path: 'loan-demand',
        data: { pageTitle: 'vksApplicationApp.loanDemand.home.title' },
        loadChildren: () => import('./loan-demand/loan-demand.module').then(m => m.LoanDemandModule),
      },
      {
        path: 'loan-details',
        data: { pageTitle: 'vksApplicationApp.loanDetails.home.title' },
        loadChildren: () => import('./loan-details/loan-details.module').then(m => m.LoanDetailsModule),
      },
      {
        path: 'amortization-details',
        data: { pageTitle: 'vksApplicationApp.amortizationDetails.home.title' },
        loadChildren: () => import('./amortization-details/amortization-details.module').then(m => m.AmortizationDetailsModule),
      },
      {
        path: 'loan-disbursement-details',
        data: { pageTitle: 'vksApplicationApp.loanDisbursementDetails.home.title' },
        loadChildren: () =>
          import('./loan-disbursement-details/loan-disbursement-details.module').then(m => m.LoanDisbursementDetailsModule),
      },
      {
        path: 'loan-repayment-details',
        data: { pageTitle: 'vksApplicationApp.loanRepaymentDetails.home.title' },
        loadChildren: () => import('./loan-repayment-details/loan-repayment-details.module').then(m => m.LoanRepaymentDetailsModule),
      },
      {
        path: 'loan-watap-details',
        data: { pageTitle: 'vksApplicationApp.loanWatapDetails.home.title' },
        loadChildren: () => import('./loan-watap-details/loan-watap-details.module').then(m => m.LoanWatapDetailsModule),
      },
      {
        path: 'vouchers',
        data: { pageTitle: 'vksApplicationApp.vouchers.home.title' },
        loadChildren: () => import('./vouchers/vouchers.module').then(m => m.VouchersModule),
      },
      {
        path: 'voucher-details',
        data: { pageTitle: 'vksApplicationApp.voucherDetails.home.title' },
        loadChildren: () => import('./voucher-details/voucher-details.module').then(m => m.VoucherDetailsModule),
      },
      {
        path: 'vouchers-history',
        data: { pageTitle: 'vksApplicationApp.vouchersHistory.home.title' },
        loadChildren: () => import('./vouchers-history/vouchers-history.module').then(m => m.VouchersHistoryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
